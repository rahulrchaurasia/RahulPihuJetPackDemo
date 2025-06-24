package com.interstellar.rahulpihujetpackdemo.data.local.model.common

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    data class Loading(val isLoading: Boolean = true) : ApiResult<Nothing>()
}

// Extension functions for easier handling
inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(data)
    return this
}

inline fun <T> ApiResult<T>.onError(action: (Exception) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) action(exception)
    return this
}

inline fun <T> ApiResult<T>.onLoading(action: (Boolean) -> Unit): ApiResult<T> {
    if (this is ApiResult.Loading) action(isLoading)
    return this
}