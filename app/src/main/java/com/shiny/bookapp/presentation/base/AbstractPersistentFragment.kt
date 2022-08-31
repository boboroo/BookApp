package com.shiny.bookapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class AbstractPersistentFragment<B: ViewDataBinding, VM: ViewModel> : AbstractBindingFragment<B>() {

    protected abstract val viewModel: VM

    protected abstract fun initViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

}