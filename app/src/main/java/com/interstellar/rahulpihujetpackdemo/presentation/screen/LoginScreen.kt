package com.interstellar.rahulpihujetpackdemo.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.data.remote.response.login.LoginResponse
import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.LoginViewModel
import com.interstellar.rahulpihujetpackdemo.presentation.components.loading.LoadingConfig
import com.interstellar.rahulpihujetpackdemo.presentation.components.loading.LoadingType
import com.interstellar.rahulpihujetpackdemo.presentation.components.loading.UniversalLoading




@Composable
fun LoginScreen(
    onLoginSuccess: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    // UI State
    val mobile by viewModel.mobile
    val password by viewModel.password
    val mobileError by viewModel.mobileError
    val passwordError by viewModel.passwordError

    var passwordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf<String?>(null) }

    // ViewModel State - Use ViewModel's isLoading directly
    val loginState by viewModel.loginState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // ← Direct from ViewModel


    // Handle only Success and Error
    LaunchedEffect(loginState) {
        when (val currentState = loginState) {
            is ApiResult.Success -> {
                Log.d("LoginScreen", "✅ Success response: ${currentState.data}")
                val response = currentState.data

                if (response.status == "Success" && response.statusNo == 0) {
                    response.masterData?.let { masterData ->
                        Log.d("LoginScreen", "🚀 Calling onLoginSuccess with: ${masterData.emailId}, ${masterData.userId}")
                        onLoginSuccess(masterData.emailId, masterData.userId)
                    } ?: run {
                        Log.e("LoginScreen", "❌ MasterData is null")
                        errorDialogMessage = "Login successful but user data is missing"
                        showError = true
                    }
                } else {
                    Log.e("LoginScreen", "❌ Login failed with status: ${response.status}")
                    errorDialogMessage = response.message ?: "Login failed"
                    showError = true
                }
            }
            is ApiResult.Error -> {
                Log.e("LoginScreen", "❌ API Error: ${currentState.exception.message}")
                errorDialogMessage = currentState.exception.message
                showError = true
            }
            // ✅ No Loading case needed - handled by ViewModel's isLoading state
            else -> {
                // Handle other states silently
            }
        }
    }

    // Use UniversalLoading with ViewModel's isLoading


    UniversalLoading(
        isLoading = isLoading,
        config = LoadingConfig(
            message = "Signing you in...",
            loadingType = LoadingType.CIRCULAR,
            showBackground = true
        )
    ) {
        LoginContent(
            mobile = mobile,
            onMobileChange = {
                if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                    viewModel.mobile.value = it
                }
            },
            password = password,
            onPasswordChange = { viewModel.password.value = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
            onLogin = { viewModel.login() },
            onNavigateToRegister = onNavigateToRegister,
            isLoading = isLoading,
            loginState = loginState,
            mobileError = mobileError,
            passwordError = passwordError
        )
    }

    // Error Dialog (same as before)
    if (showError && errorDialogMessage != null) {
        AlertDialog(
            onDismissRequest = {
                showError = false
                errorDialogMessage = null
                viewModel.clearError()
            },
            title = { Text("Login Failed") },
            text = { Text(errorDialogMessage ?: "An unknown error occurred") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showError = false
                        errorDialogMessage = null
                        viewModel.clearError()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {


        LoginScreen(
            onLoginSuccess = { _, _ -> {} }, // ✅ Fixed - takes 2 parameters
            onNavigateToRegister = {}
        )
    }
}

@Composable
private fun LoginContent(
    mobile: String,
    onMobileChange: (String) -> Unit,
    mobileError: String? = null,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String? = null,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    isLoading: Boolean,
    loginState: ApiResult<LoginResponse>?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Sign in to your account",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Mobile Number Field
        OutlinedTextField(
            value = mobile,
            onValueChange = onMobileChange,
            label = { Text("Mobile Number") },
            placeholder = { Text("Enter 10-digit mobile number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = mobileError != null,
            supportingText = mobileError?.let { { Text(it) } },
        )

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (mobile.length == 10 && password.length >= 6 && !isLoading) {
                        onLogin()
                    }
                }
            ),
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            isError = passwordError != null,
            supportingText = passwordError?.let { { Text(it) } },
        )

        // Login Button
        Button(
            onClick = {
                Log.d("LoginScreen", "🔄 Login button clicked - Mobile: $mobile")
                onLogin()
            },
            enabled = !isLoading && mobile.length == 10 && password.length >= 6,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {

            Text("Sign In")
//            if (isLoading) {
//                InlineLoading(
//                    message = "Signing In...",
//                    size = 20.dp,
//                    color = MaterialTheme.colorScheme.onPrimary
//                )
//            } else {
//                Text("Sign In")
//            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Navigation
        TextButton(
            onClick = onNavigateToRegister,
            enabled = !isLoading
        ) {
            Text("Don't have an account? Sign Up")
        }

        // Debug info
        Column(
            modifier = Modifier.padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                Text(
                    text = "🔄 Calling API...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "State: ${loginState?.javaClass?.simpleName ?: "Initial"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Preview(showBackground = true, name = "Login Content - Default")
@Composable
private fun LoginContentPreview() {
    MaterialTheme {
        val mobileError : String
        LoginContent(
            mobile = "9224624994",
            onMobileChange = {},
            password = "123456",
            onPasswordChange = {},
            passwordVisible = false,
            onPasswordVisibilityChange = {},
            onLogin = {},
            onNavigateToRegister = {},
            isLoading = false,
            loginState = null,
            mobileError = null
        )
    }
}