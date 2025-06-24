package com.interstellar.rahulpihujetpackdemo.data.remote.api


import com.interstellar.rahulpihujetpackdemo.data.local.model.request.LoginRequest
import com.interstellar.rahulpihujetpackdemo.data.local.model.response.LoginResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    
    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
    
    // Add other auth endpoints here
    // @POST("api/register")
    // suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
    
    // @POST("api/logout") 
    // suspend fun logout(@Body logoutRequest: LogoutRequest): Response<LogoutResponse>
}