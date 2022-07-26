package com.shiny.mysearch.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger

abstract class AbstractBindingFragment<B: ViewDataBinding> : Fragment() {

    private lateinit var _binding: B
    protected val binding get() = _binding!!

    protected abstract fun getResourceId() : Int

    protected abstract fun initView(root: View)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d("onAttach")

        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Remove all fragments from the childFragmentManager,
                // but exclude the first added child fragment.
                // This child fragment will be deleted with its parent.
                if (childFragmentManager.backStackEntryCount> 0) {
                    childFragmentManager.popBackStack()
                    return
                }
                // Delete parent fragment
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<B>(inflater, getResourceId(), container, false)
        initView(binding.root)
        return binding.root
    }

}