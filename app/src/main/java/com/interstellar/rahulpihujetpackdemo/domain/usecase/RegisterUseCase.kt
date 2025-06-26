package com.interstellar.rahulpihujetpackdemo.domain.usecase

import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.request.RegisterRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterResponse

import com.interstellar.rahulpihujetpackdemo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Note : RegisterUseCase depends on AuthRepository (interface)
/*
✅ Loosely coupled
✅ Easy to test with fake/moc
✅ Respects SOLID/DIP

 */
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository //<-- interface!
)
{
    suspend operator fun invoke(
        registerRequest: RegisterRequest
    ): Flow<ApiResult<RegisterResponse>> {
        return authRepository.register(registerRequest)

    }
}
