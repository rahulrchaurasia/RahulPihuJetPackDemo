package com.interstellar.rahulpihujetpackdemo.data.repository

import com.interstellar.rahulpihujetpackdemo.data.remote.api.AuthApiService
import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.request.LoginRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.request.RegisterRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.response.login.LoginResponse
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterResponse
import com.interstellar.rahulpihujetpackdemo.data.repository.base.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

import com.interstellar.rahulpihujetpackdemo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

@Singleton
 class AuthRepositoryImpl  @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRepository, BaseRepository() {
    override suspend fun register(request: RegisterRequest): Flow<ApiResult<RegisterResponse>> {
        return safeApiCall {
            authApiService.register(request)
        }
    }

    override suspend fun login(mobile: String, password: String): Flow<ApiResult<LoginResponse>> {
        return safeApiCall {
            authApiService.login(LoginRequest(mobile, password))
        }
    }
}