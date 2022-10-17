package com.shiny.bookapp.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shiny.bookapp.R

abstract class AbstractBottomDialogFragment<B: ViewDataBinding>: BottomSheetDialogFragment() {

    private lateinit var _binding: B
    protected val binding get() = _binding

    protected abstract fun getResourceId(): Int
    abstract fun initView()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BaseBottomSheetDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getResourceId(), container, false)
        initView()
        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val bottomSheetBehavior = (dialog as BottomSheetDialog).behavior

        dialog.setCanceledOnTouchOutside(false)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL // Configuration 변경되어도 모든 속성 유지

        return dialog
    }

}