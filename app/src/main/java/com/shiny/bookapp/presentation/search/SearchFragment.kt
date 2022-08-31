package com.shiny.bookapp.presentation.main

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.orhanobut.logger.Logger
import com.shiny.bookapp.MyApplication
import com.shiny.bookapp.R
import com.shiny.bookapp.databinding.FragmentSearchBinding
import com.shiny.bookapp.presentation.base.AbstractPersistentFragment
import com.shiny.bookapp.presentation.base.AbstractViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : AbstractPersistentFragment<FragmentSearchBinding, AbstractViewModel>() {

    override val viewModel: SearchResultViewModel by activityViewModels()

    companion object {
        fun newInstance() = SearchFragment()
    }


    override fun getResourceId(): Int {
        return R.layout.fragment_search
    }


    override fun initView(root: View) {
        initSearch()
    }


    override fun onResume() {
        super.onResume()

        if (viewModel.keyword.isNotBlank() == true) {
            binding.inputKeyword.setText(viewModel.keyword)
        }

        // fragment 교체나 홈 키로 앱을 나갔다 들어오는 등의 상황에서, 검색된 결과가 있다면 키보드를 내려주고 없다면 올려줍니다!
        binding.root.handler?.postDelayed({
            showSoftKeyboard(binding.txtResponse.text.isNullOrBlank())
        }, 300)
    }


    override fun onPause() {
        super.onPause()
        showSoftKeyboard(false)
    }


    private fun initSearch() {
        binding.btnSubmit.setOnClickListener {
            updatedKeywordFromInput()
        }
        binding.inputKeyword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatedKeywordFromInput()
                true
            } else {
                false
            }
        }
        binding.inputKeyword.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatedKeywordFromInput()
                true
            } else {
                false
            }
        }
    }


    private fun updatedKeywordFromInput(forceQuery: Boolean = false) {
        binding.inputKeyword.text?.trim().toString().let { keyword ->
            if (keyword.isNotBlank()) {
                showSoftKeyboard(false) // 사용자에게 검색된 결과가 잘 보여지도록 키보드를 내림
                viewModel.setQuery(keyword, forceQuery)
            }
        }
    }


    private fun showSoftKeyboard(show: Boolean) {
        binding.inputKeyword.let { edtxt ->
            val imm = MyApplication.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (show) {
                imm.showSoftInput(edtxt, InputMethodManager.SHOW_FORCED)
            } else {
                imm.hideSoftInputFromWindow(edtxt.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN)
            }
        }
    }


    override fun initViewModel() {
        viewModel.progressBar.observe(viewLifecycleOwner, { tf ->
            if (tf) {
                // TODO Show progress bar UI
                Logger.d("Progress bar is displayed (TODO UI)")
            } else {
                // TODO Hide progress bar UI
                Logger.d("Progress bar is hidden (TODO UI)")
            }
        })

        viewModel.loadData()

        viewModel.searchResults.observe(viewLifecycleOwner, { uiEvent ->
            when(uiEvent) {
                is AbstractViewModel.UiEvent.DataUpdate -> {
                    // FIXME retry dialog로 변경한 후 지우기
                    binding.txtResponse.setOnClickListener(null)

                    binding.txtResponse.text =
                        if (!uiEvent.data.isEmpty()) uiEvent.data.toString()
                        else MyApplication.context.resources.getString(R.string.no_search_data)
                }
                is AbstractViewModel.UiEvent.Error -> {

                    binding.txtResponse.text = uiEvent.message

                    if (uiEvent.retry) {
                        // FIXME retry dialog로 변경하기
                        binding.txtResponse.setOnClickListener { v ->
                            Logger.d("Retry")
                            updatedKeywordFromInput(true)
                        }
                    }
                }
            }
        })
    }

}
