package com.interstellar.rahulpihujetpackdemo.domain.usecase

import com.interstellar.rahulpihujetpackdemo.data.local.model.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.local.model.response.LoginResponse

import com.interstellar.rahulpihujetpackdemo.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        mobile: String,
        password: String
    ): Flow<ApiResult<LoginResponse>> {

        // Add validation logic here
        return authRepository.login(mobile, password)
//        return if (validateInput(mobile, password)) {
//            authRepository.login(mobile, password)
//        } else {
//            throw IllegalArgumentException("Invalid mobile number or password")
//        }
    }

//    private fun validateInput(mobile: String, password: String): Boolean {
//        return mobile.isNotBlank() &&
//                mobile.length == 10 &&
//                mobile.all { it.isDigit() } &&
//                password.isNotBlank() &&
//                password.length >= 6
//    }
}