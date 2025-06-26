package com.interstellar.rahulpihujetpackdemo.data.repository


import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.request.LoginRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.response.login.LoginResponse
import com.interstellar.rahulpihujetpackdemo.data.remote.api.AuthApiService
import com.interstellar.rahulpihujetpackdemo.data.remote.request.RegisterRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterResponse
import com.interstellar.rahulpihujetpackdemo.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class AuthRepository @Inject constructor(
//    private val authApiService: AuthApiService
//) : BaseRepository() {
//
//    suspend fun login(
//        mobile: String,
//        password: String
//    ): Flow<ApiResult<LoginResponse>> {
//        return safeApiCall {
//            authApiService.login(
//                LoginRequest(
//                    mobile = mobile,
//                    password = password
//                )
//            )
//        }
//    }
//
//    suspend fun register(
//       registerRequest: RegisterRequest
//    ): Flow<ApiResult<RegisterResponse>> {
//        return safeApiCall {
//            authApiService.register(registerRequest)
//
//        }
//    }
//}