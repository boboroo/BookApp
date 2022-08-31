package com.shiny.bookapp.domain.network

import com.shiny.bookapp.MyApplication
import com.shiny.bookapp.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

object DefaultErrorHandler: ErrorHandler {

    override fun getError(exception: Exception): Exception {
        val resources = MyApplication.context.resources

        return when (exception) {
            is SocketTimeoutException -> NetworkConnectionException(message = resources.getString(R.string.unstable_internet_connection), cause = exception)
            is ConnectException -> NetworkConnectionException(message = resources.getString(R.string.unstable_internet_connection), cause = exception)
            is HttpException -> {
                when(exception.code()) {
                    /* Http Status Codes
                    * 400 : 클라이언트가 잘못된 문법 등으로 올바르지 못한 요청을 보내서 서버가 요청을 이해하지 못할 때.
                    * 401 : 인증되지 않은 사용자가 인증이 필요한 리소스를 요청하는 경우의 응답.
                    * 403 : Forbidden. 리소스에 접근할 권한이 없는 클라이언트일 경우 발생. (접근 거부)
                    * 404 : 리소스가 존재하지 않음. 인증되지 않은 클라이언트로부터 리소스를 숨기기 위해 403 대신 404를 전송하기도 함.
                    */
                    401 -> UnauthorizedException(message = resources.getString(R.string.unauthorized), cause = exception)
                    400 or 403 or 404 -> {
                        UnknownException(cause = exception)
                    }
                    in 500 .. 599 -> ServerFailException(message = resources.getString(R.string.server_fail), cause = exception)
                    else -> NetworkConnectionException(message = resources.getString(R.string.internet_connection), cause = exception)
                }
            }
            else -> UnknownException(cause = exception)
        }
    }
}