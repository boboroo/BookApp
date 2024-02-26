package com.shiny.bookapp.domain.extension

import com.shiny.bookapp.domain.network.ResultData

suspend fun <T, R> ResultData<T>.mapNetworkResult(convertData: suspend (T) -> R): ResultData<R> {
    return when (this) {
        is ResultData.Success -> ResultData.Success(map(convertData))
        is ResultData.Fail -> ResultData.Fail(this.exception)
    }
}


private suspend inline fun <T, R> ResultData<T>.map(convertData: suspend (T) -> R): R =
    convertData(toData())


fun <T> ResultData<T>.toData(): T {
    return when (this) {
        is ResultData.Success -> this.data
        is ResultData.Fail -> throw this.exception
    }
}