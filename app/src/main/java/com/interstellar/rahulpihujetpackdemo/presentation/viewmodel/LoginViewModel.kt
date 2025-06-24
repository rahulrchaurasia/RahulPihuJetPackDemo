package com.interstellar.rahulpihujetpackdemo.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interstellar.rahulpihujetpackdemo.data.local.model.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.local.model.response.LoginResponse
import com.interstellar.rahulpihujetpackdemo.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val mobile = mutableStateOf("")
    val password = mutableStateOf("")
    val mobileError = mutableStateOf<String?>(null)
    val passwordError = mutableStateOf<String?>(null)

    private val _loginState = MutableStateFlow<ApiResult<LoginResponse>?>(null)
    val loginState: StateFlow<ApiResult<LoginResponse>?> = _loginState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private fun validateInputs(): Boolean {
        var valid = true

        if (mobile.value.isBlank() || mobile.value.length != 10 || !mobile.value.all { it.isDigit() }) {
            mobileError.value = "Enter valid 10-digit mobile"
            valid = false
        } else {
            mobileError.value = null
        }

        if (password.value.isBlank() || password.value.length < 6) {
            passwordError.value = "Password must be at least 6 characters"
            valid = false
        } else {
            passwordError.value = null
        }

        return valid
    }

    fun login() {
        Log.d("LoginViewModel", "Login called with mobile: $mobile")

        if (validateInputs()) {
            viewModelScope.launch {
                try {
                    _isLoading.value = true // ✅ Set immediately when login starts

                    delay(3000) // 2 seconds delay
                    loginUseCase(mobile.value, password.value)
                        .catch { e ->
                            // Handle flow exceptions
                            Log.e("LoginViewModel", "Flow exception: ${e.message}")
                            _isLoading.value = false
                            _errorMessage.value = e.message ?: "An unexpected error occurred"
                        }
                        .collect { result ->
                            Log.d("LoginViewModel", "UseCase result: $result")
                            _loginState.value = result

                            when (result) {

                                is ApiResult.Success -> {
                                    Log.d("LoginViewModel", "Success: ${result.data}")
                                    _isLoading.value = false
                                    _errorMessage.value = null
                                    // Don't clear loginState here - let UI handle it
                                }
                                is ApiResult.Error -> {
                                    Log.e("LoginViewModel", "Error: ${result.exception.message}")
                                    _isLoading.value = false
                                    _errorMessage.value = result.exception.message
                                }

                                is ApiResult.Loading -> {}
                            }
                        }
                } catch (e: Exception) {
                    Log.e("LoginViewModel", "Exception in login: ${e.message}")
                    _isLoading.value = false
                    _errorMessage.value = e.message ?: "An unexpected error occurred"
                }
            }
        }

    }

    fun clearError() {
        _errorMessage.value = null
        // Also clear login state to reset UI
        _loginState.value = null
    }
}