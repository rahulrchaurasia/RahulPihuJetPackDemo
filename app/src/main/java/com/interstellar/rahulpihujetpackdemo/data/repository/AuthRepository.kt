package com.interstellar.rahulpihujetpackdemo.data.repository


import com.interstellar.rahulpihujetpackdemo.data.local.model.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.local.model.request.LoginRequest
import com.interstellar.rahulpihujetpackdemo.data.local.model.response.LoginResponse
import com.interstellar.rahulpihujetpackdemo.data.remote.api.AuthApiService
import com.interstellar.rahulpihujetpackdemo.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
) : BaseRepository() {

    suspend fun login(
        mobile: String,
        password: String
    ): Flow<ApiResult<LoginResponse>> {
        return safeApiCall {
            authApiService.login(
                LoginRequest(
                    mobile = mobile,
                    password = password
                )
            )
        }
    }
}