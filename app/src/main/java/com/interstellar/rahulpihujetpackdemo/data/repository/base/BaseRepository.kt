package com.interstellar.rahulpihujetpackdemo.data.repository.base

import android.util.Log
import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.exception.ApiException
import com.interstellar.rahulpihujetpackdemo.data.remote.exception.NetworkException
import com.interstellar.rahulpihujetpackdemo.data.remote.exception.TimeoutException

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository {

    protected fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<ApiResult<T>> = flow {
        try {
            emit(ApiResult.Loading(true))

            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResult.Success(data))
                    Log.d("BaseRepository", "✅ API Success: ${response.code()}")
                } ?: emit(ApiResult.Error(ApiException("Empty response body")))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Log.e("BaseRepository", "❌ API Error: ${response.code()} - $errorMessage")
                emit(
                    ApiResult.Error(
                    ApiException(
                        message = errorMessage,
                        statusCode = response.code(),
                        errorBody = errorMessage
                    )
                ))
            }
        } catch (e: SocketTimeoutException) {
            Log.e("BaseRepository", "⏰ Timeout Error: ${e.message}")
            emit(ApiResult.Error(TimeoutException("Request timeout. Please try again.")))
        } catch (e: IOException) {
            Log.e("BaseRepository", "🌐 Network Error: ${e.message}")
            emit(ApiResult.Error(NetworkException("Network error. Please check your connection.")))
        } catch (e: Exception) {
            Log.e("BaseRepository", "💥 Unexpected Error: ${e.message}")
            emit(ApiResult.Error(e))
        }

    }.flowOn(Dispatchers.IO)
}