package com.shiny.bookapp.domain.network

/*
* 앱에서 처리되고 있는 정의된 Custom Exception들
*/


/** 네트워크 작업 실패로 retry 처리 가능성이 있는 경우 발생시키는 예외 */
class NetworkConnectionException(message: String, cause: Throwable): Exception(message, cause)

/** 사용자 인증이 필요할 때 발생시키는 예외 */
class UnauthorizedException(message: String, cause: Throwable): Exception(message, cause)

/** 서버에서 발생된 문제시의 예외 */
class ServerFailException(message: String, cause: Throwable): Exception(message, cause)

/** 예기치 않은 예외, 알 수 없는 예외 */
class UnknownException(cause: Throwable): Exception(cause)