package com.shiny.mysearch.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class AbstractViewModel : ViewModel() {

    protected var _progressBar = MutableLiveData<Boolean>()
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
        data class ShowProgress<Nothing>(val show: Boolean) : UiEvent<Nothing>()
        data class Success<T>(val data: T) : UiEvent<T>()
        data class Error<Nothing>(val message: String) : UiEvent<Nothing>()
    }

}