package com.interstellar.rahulpihujetpackdemo.domain.repository

import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.request.RegisterRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.response.login.LoginResponse
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(request: RegisterRequest): Flow<ApiResult<RegisterResponse>>
    suspend fun login(mobile: String, password: String): Flow<ApiResult<LoginResponse>>

}