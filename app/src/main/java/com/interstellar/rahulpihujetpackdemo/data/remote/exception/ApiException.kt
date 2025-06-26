package com.interstellar.rahulpihujetpackdemo.data.remote.exception

class ApiException(
    message: String,
    val statusCode: Int? = null,
    val errorBody: String? = null
) : Exception(message)

class NetworkException(message: String) : Exception(message)
class TimeoutException(message: String) : Exception(message)