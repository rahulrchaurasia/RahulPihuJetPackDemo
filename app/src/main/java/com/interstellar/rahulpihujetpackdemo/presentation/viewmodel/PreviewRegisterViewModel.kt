package com.interstellar.rahulpihujetpackdemo.presentation.viewmodel

import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.request.RegisterRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.response.login.LoginResponse
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterData
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterResponse

import com.interstellar.rahulpihujetpackdemo.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.flow


import com.interstellar.rahulpihujetpackdemo.domain.repository.AuthRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PreviewRegisterViewModel : RegisterViewModel(
    registerUseCase = RegisterUseCase(
        authRepository = object : AuthRepository {
            override suspend fun register(request: RegisterRequest): Flow<ApiResult<RegisterResponse>> {
                return flow {
                    emit(
                        ApiResult.Success(
                            RegisterResponse(
                                masterData = RegisterData(
                                    message = "Preview Message",
                                    result = "Success",
                                    savedStatus = 1
                                ),
                                message = "Registered from preview",
                                status = "Success",
                                statusNo = 200
                            )
                        )
                    )
                }
            }

            override suspend fun login(mobile: String, password: String): Flow<ApiResult<LoginResponse>> {
                return flow {
                    emit(
                        ApiResult.Success(
                            LoginResponse(
                                message = "Login mock",
                                status = "Success",
                                statusNo = 200,
                                masterData = null
                            )
                        )
                    )
                }
            }
        }
    )
)
