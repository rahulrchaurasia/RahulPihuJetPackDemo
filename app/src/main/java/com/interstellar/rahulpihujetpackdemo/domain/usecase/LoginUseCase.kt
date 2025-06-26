package com.interstellar.rahulpihujetpackdemo.domain.usecase

import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.response.login.LoginResponse
import com.interstellar.rahulpihujetpackdemo.domain.repository.AuthRepository


import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Note : LoginUseCase depends on AuthRepository (interface)
/*
✅ Loosely coupled
✅ Easy to test with fake/moc
✅ Respects SOLID/DIP

 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository  //<-- interface!
) {
    suspend operator fun invoke(
        mobile: String,
        password: String
    ): Flow<ApiResult<LoginResponse>> {

        // Add validation logic here
        return authRepository.login(mobile, password)

    }




}