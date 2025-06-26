package com.interstellar.rahulpihujetpackdemo.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.request.RegisterRequest
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterData
import com.interstellar.rahulpihujetpackdemo.data.remote.response.register.RegisterResponse
import com.interstellar.rahulpihujetpackdemo.domain.usecase.RegisterUseCase
import com.interstellar.rahulpihujetpackdemo.presentation.util.formatDOB
import com.interstellar.rahulpihujetpackdemo.presentation.util.isValidDOB
import com.interstellar.rahulpihujetpackdemo.presentation.util.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    // Form fields
    val fullName = mutableStateOf("")
    val mobile = mutableStateOf("")
    val email = mutableStateOf("")
    val street = mutableStateOf("")
    val city = mutableStateOf("")
    val state = mutableStateOf("")
    val pincode = mutableStateOf("")
    val country = mutableStateOf("")
    val selectedGender = mutableStateOf("")
    val dobRaw = mutableStateOf("")
    val occupation = mutableStateOf("")
    val company = mutableStateOf("")
    val maritalStatus = mutableStateOf(setOf<String>())

    // Error states
    val fullNameError = mutableStateOf<String?>(null)
    val mobileError = mutableStateOf<String?>(null)
    val emailError = mutableStateOf<String?>(null)
    val streetError = mutableStateOf<String?>(null)
    val cityError = mutableStateOf<String?>(null)
    val stateError = mutableStateOf<String?>(null)
    val pincodeError = mutableStateOf<String?>(null)
    val countryError = mutableStateOf<String?>(null)
    val genderError = mutableStateOf<String?>(null)
    val dobError = mutableStateOf<String?>(null)
    val occupationError = mutableStateOf<String?>(null)
    val companyError = mutableStateOf<String?>(null)

    // API states
    private val _registerState = MutableStateFlow<ApiResult<RegisterResponse>?>(null)
    val registerState: StateFlow<ApiResult<RegisterResponse>?> = _registerState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Gender options
    val genderOptions = listOf("Male", "Female", "Other")

    // Marital status options
    val maritalOptions = listOf("Single", "Married", "Divorced", "Widowed")

    // Real-time validation functions
    fun updateFullName(input: String) {
        if (input.all { it.isLetter() || it.isWhitespace() }) {
            fullName.value = input
            fullNameError.value = if (input.isBlank()) "Full name is required" else null
        }
    }

    fun updateMobile(input: String) {
        val digits = input.filter { it.isDigit() }
        if (digits.length <= 10) {
            mobile.value = digits
            mobileError.value = when {
                digits.isEmpty() -> "Mobile number is required"
                digits.length < 10 -> "Mobile must be 10 digits"
                else -> null
            }
        }
    }

    fun updateEmail(input: String) {
        if (input.length <= 50) {
            email.value = input
            emailError.value = when {
                input.isEmpty() -> "Email is required"
                !isValidEmail(input) -> "Invalid email format"
                else -> null
            }
        }
    }

    fun updateStreet(input: String) {
        street.value = input
        streetError.value = if (input.isBlank()) "Street address is required" else null
    }

    fun updateCity(input: String) {
        if (input.all { it.isLetter() || it.isWhitespace() }) {
            city.value = input
            cityError.value = if (input.isBlank()) "City is required" else null
        }
    }

    fun updateState(input: String) {
        if (input.all { it.isLetter() || it.isWhitespace() }) {
            state.value = input
            stateError.value = if (input.isBlank()) "State is required" else null
        }
    }

    fun updatePincode(input: String) {
        val digits = input.filter { it.isDigit() }
        if (digits.length <= 6) {
            pincode.value = digits
            pincodeError.value = when {
                digits.isEmpty() -> "Pincode is required"
                digits.length < 6 -> "Pincode must be 6 digits"
                else -> null
            }
        }
    }

    fun updateCountry(input: String) {
        if (input.all { it.isLetter() || it.isWhitespace() }) {
            country.value = input
            countryError.value = if (input.isBlank()) "Country is required" else null
        }
    }

    fun updateGender(gender: String) {
        selectedGender.value = gender
        genderError.value = null
    }

    fun updateDOB(input: String) {
        val digits = input.filter { it.isDigit() }
        if (digits.length <= 8) {
            dobRaw.value = digits
            val formattedDob = formatDOB(digits)
            dobError.value = when {
                digits.isEmpty() -> "Date of birth is required"
                digits.length < 8 -> "Complete date is required"
                !isValidDOB(formattedDob) -> "Invalid date format"
                else -> null
            }
        }
    }

    fun updateOccupation(input: String) {
        occupation.value = input
        occupationError.value = if (input.isBlank()) "Occupation is required" else null
    }

    fun updateCompany(input: String) {
        company.value = input
        companyError.value = if (input.isBlank()) "Company is required" else null
    }

    fun updateMaritalStatus(status: String, isChecked: Boolean) {
        maritalStatus.value = if (isChecked) {
            maritalStatus.value + status
        } else {
            maritalStatus.value - status
        }
    }

    private fun validateAllFields(): Boolean {
        var isValid = true

        // Validate all fields
        if (fullName.value.isBlank()) {
            fullNameError.value = "Full name is required"
            isValid = false
        }

        if (mobile.value.length != 10) {
            mobileError.value = "Mobile must be 10 digits"
            isValid = false
        }

        if (email.value.isBlank()) {
            emailError.value = "Email is required"
            isValid = false
        } else if (!isValidEmail(email.value)) {
            emailError.value = "Invalid email format"
            isValid = false
        }

        if (street.value.isBlank()) {
            streetError.value = "Street address is required"
            isValid = false
        }

        if (city.value.isBlank()) {
            cityError.value = "City is required"
            isValid = false
        }

        if (state.value.isBlank()) {
            stateError.value = "State is required"
            isValid = false
        }

        if (pincode.value.length != 6) {
            pincodeError.value = "Pincode must be 6 digits"
            isValid = false
        }

        if (country.value.isBlank()) {
            countryError.value = "Country is required"
            isValid = false
        }

        if (selectedGender.value.isBlank()) {
            genderError.value = "Please select gender"
            isValid = false
        }

        if (dobRaw.value.length != 8) {
            dobError.value = "Complete date is required"
            isValid = false
        } else if (!isValidDOB(formatDOB(dobRaw.value))) {
            dobError.value = "Invalid date format"
            isValid = false
        }

        if (occupation.value.isBlank()) {
            occupationError.value = "Occupation is required"
            isValid = false
        }

        if (company.value.isBlank()) {
            companyError.value = "Company is required"
            isValid = false
        }

        return isValid
    }

    fun register() {
        Log.d("RegisterViewModel", "Register called")

        if (validateAllFields()) {
            viewModelScope.launch {
                try {
                    _isLoading.value = true

                    val registerData = RegisterRequest(
                        fullname = fullName.value,
                        mobile = mobile.value,
                        email = email.value,
                        street = street.value,
                        city = city.value,
                        state = state.value,
                        pincode = pincode.value,
                        country = country.value,
                        gender = selectedGender.value,
                        dob = formatDOB(dobRaw.value),
                        occupation = occupation.value,
                        company = company.value,

                        maritalstatus = maritalStatus.value.firstOrNull() ?: "",
                    )

                    delay(2000) // Simulate network delay

                    registerUseCase(registerData)
                        .catch { e ->
                            Log.e("RegisterViewModel", "Flow exception: ${e.message}")
                            _isLoading.value = false
                            _errorMessage.value = e.message ?: "An unexpected error occurred"
                        }
                        .collect { result ->
                            Log.d("RegisterViewModel", "UseCase result: $result")
                            _registerState.value = result

                            when (result) {
                                is ApiResult.Success -> {
                                    Log.d("RegisterViewModel", "Success: ${result.data}")
                                    _isLoading.value = false
                                    _errorMessage.value = null
                                }

                                is ApiResult.Error -> {
                                    Log.e("RegisterViewModel", "Error: ${result.exception.message}")
                                    _isLoading.value = false
                                    _errorMessage.value = result.exception.message
                                }

                                is ApiResult.Loading -> {
                                    // Loading state is handled by _isLoading
                                }
                            }
                        }
                } catch (e: Exception) {
                    Log.e("RegisterViewModel", "Exception in register: ${e.message}")
                    _isLoading.value = false
                    _errorMessage.value = e.message ?: "An unexpected error occurred"
                }
            }
        } else {
            Log.d("RegisterViewModel", "Form validation failed")
        }
    }

     fun clearError() {
        _errorMessage.value = null
        _registerState.value = null
    }

    private fun clearAllErrors() {
        fullNameError.value = null
        mobileError.value = null
        emailError.value = null
        streetError.value = null
        cityError.value = null
        stateError.value = null
        pincodeError.value = null
        countryError.value = null
        genderError.value = null
        dobError.value = null
        occupationError.value = null
        companyError.value = null
    }

    fun resetForm() {
        // Clear all form fields
        fullName.value = ""
        mobile.value = ""
        email.value = ""
        street.value = ""
        city.value = ""
        state.value = ""
        pincode.value = ""
        country.value = ""
        selectedGender.value = ""
        dobRaw.value = ""
        occupation.value = ""
        company.value = ""
        maritalStatus.value = setOf()

        // Clear all errors
        clearAllErrors()
        clearError()
    }

    // Helper function to check if form is valid for button state
    fun isFormValid(): Boolean {
        return fullName.value.isNotBlank() &&
                mobile.value.length == 10 &&
                email.value.isNotBlank() &&
                street.value.isNotBlank() &&
                city.value.isNotBlank() &&
                state.value.isNotBlank() &&
                pincode.value.length == 6 &&
                country.value.isNotBlank() &&
                selectedGender.value.isNotBlank() &&
                dobRaw.value.length == 8 &&
                occupation.value.isNotBlank() &&
                company.value.isNotBlank() &&
                isValidEmail(email.value) &&
                isValidDOB(formatDOB(dobRaw.value)) &&
                fullNameError.value == null &&
                mobileError.value == null &&
                emailError.value == null &&
                streetError.value == null &&
                cityError.value == null &&
                stateError.value == null &&
                pincodeError.value == null &&
                countryError.value == null &&
                genderError.value == null &&
                dobError.value == null &&
                occupationError.value == null &&
                companyError.value == null
    }
}
