package com.shiny.bookapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.orhanobut.logger.Logger
import com.shiny.bookapp.MyApplication
import com.shiny.bookapp.domain.network.DefaultErrorHandler
import com.shiny.bookapp.domain.network.ErrorHandler
import com.shiny.bookapp.domain.network.NetworkConnectionException
import com.shiny.bookapp.domain.network.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException

/* 서버에서 내려주는 정의된 code들
const val REST_SUCCESS = "E000"
const val REST_CODE_NO_SEARCH_BOOK = "C000"
const val REST_CODE_ERROR_TOKEN_EXPIRED = "E991"
const val REST_CODE_ERROR_JWT_REFRESH = "E995"

fun <T> MainResponse<T>.toResultData() : ResultData<T> {
    return when(code){
        REST_SUCCESS -> ResultData.Success(data)
        REST_CODE_ERROR_JWT_REFRESH -> throw JwtRefreshException(message)
        REST_CODE_ERROR_TOKEN_EXPIRED -> throw TokenExpireException(message)
        else -> throw ServerFailException(message)
    }
}*/


fun isNetworkState(): Boolean {
    val connectivityManager: ConnectivityManager =
        MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false

        return if (networkInfo.isConnected != true) {
            false
        } else {
            when (networkInfo.type) {
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_WIMAX -> true
                else -> false
            }
        }
    }
}


suspend fun <T, R> ResultData<T>.mapNetworkResult(convertData: suspend (T) -> R): ResultData<R> {
    return when (this) {
        is ResultData.Success -> ResultData.Success(map(convertData))
        is ResultData.Fail -> ResultData.Fail(this.exception)
    }
}


suspend inline fun <T, R> ResultData<T>.map(convertData: suspend (T) -> R): R =
    convertData(toModel())


fun <T> ResultData<T>.toModel(): T {
    return when (this) {
        is ResultData.Success -> this.data
        is ResultData.Fail -> throw this.exception
    }
}


suspend fun <T> networkHandling(retry: Int = 0, errorHandler: ErrorHandler = DefaultErrorHandler, block: suspend () -> T): ResultData<T> {

    return try {
        if (!isNetworkState()) {
            throw ConnectException()
        }

        ResultData.Success(block())

    } catch (e: Exception) {
        Logger.e(e.stackTraceToString(), e)

        if (e is SocketTimeoutException && retry < 2) {
            Logger.d("SocketTimeoutException caught. retry count:$retry.")

            withContext(Dispatchers.IO) {
                delay(1000L) // retry term
            }
            return networkHandling(retry = retry + 1, errorHandler = errorHandler) { block() }
        }

        return ResultData.Fail(errorHandler.getError(e))
    }
}