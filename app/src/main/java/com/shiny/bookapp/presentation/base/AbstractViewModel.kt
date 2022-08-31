package com.shiny.bookapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class AbstractViewModel : ViewModel() {

    protected val _progressBar = MutableLiveData<Boolean>()
    val progressBar : LiveData<Boolean> get() = _progressBar

    private var firstLoadData = true

    protected abstract fun loadApi()


    fun loadData(force: Boolean = false) {
        if(firstLoadData || force) {
            loadApi()
            firstLoadData = false
        }
    }


    sealed class UiEvent<T> {
        data class DataUpdate<T>(val data: T) : UiEvent<T>()
        data class Error<Nothing>(val message: String? = null, val retry: Boolean = false) : UiEvent<Nothing>()
    }

}