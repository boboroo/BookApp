package com.shiny.bookapp.domain.network

sealed class ResultData<out DATA> {
    data class Success<DATA>(val data : DATA) : ResultData<DATA>()
    data class Fail<Nothing>(val exception: Exception) : ResultData<Nothing>()
}