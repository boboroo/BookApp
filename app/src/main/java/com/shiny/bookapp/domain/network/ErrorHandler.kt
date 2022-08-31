package com.shiny.bookapp.domain.network

import java.lang.Exception

interface ErrorHandler {
    /**
     * Exception들을 앱에서 처리되고 있는 Custom Exception(NetworkException.kt 참조)으로 변경한다.
     * Exception들을 Custom Exception으로 의미있게 그룹화한다.
     * API 마다 별도로 구현하여 해당 API에 맞게 에러를 처리할 수 있게끔 한다.
     */
    fun getError(exception: Exception): Exception
}