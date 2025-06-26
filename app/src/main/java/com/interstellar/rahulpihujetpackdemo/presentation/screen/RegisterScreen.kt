package com.interstellar.rahulpihujetpackdemo.presentation.screen



import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.interstellar.rahulpihujetpackdemo.data.remote.common.ApiResult
import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.RegisterViewModel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.interstellar.rahulpihujetpackdemo.presentation.util.formatDOB
import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.PreviewRegisterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    // Collect states from ViewModel
    val fullName by viewModel.fullName
    val mobile by viewModel.mobile
    val email by viewModel.email
    val street by viewModel.street
    val city by viewModel.city
    val state by viewModel.state
    val pincode by viewModel.pincode
    val country by viewModel.country
    val selectedGender by viewModel.selectedGender
    val dobRaw by viewModel.dobRaw
    val occupation by viewModel.occupation
    val company by viewModel.company
    val maritalStatus by viewModel.maritalStatus

    // Error states
    val fullNameError by viewModel.fullNameError
    val mobileError by viewModel.mobileError
    val emailError by viewModel.emailError
    val streetError by viewModel.streetError
    val cityError by viewModel.cityError
    val stateError by viewModel.stateError
    val pincodeError by viewModel.pincodeError
    val countryError by viewModel.countryError
    val genderError by viewModel.genderError
    val dobError by viewModel.dobError
    val occupationError by viewModel.occupationError
    val companyError by viewModel.companyError

    // API states
    val registerState by viewModel.registerState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val isFormValid by remember { derivedStateOf { viewModel.isFormValid() } }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current


    // Handle register state changes
    LaunchedEffect(registerState) {
        when (val currentState = registerState) {
            is ApiResult.Success -> {
                val response = currentState.data
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                onRegisterSuccess()
            }

            is ApiResult.Error -> {
                //errorDialogMessage = response.message ?: "Login failed"
                Toast.makeText(context, currentState.exception.message, Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

// Handle error messages
    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Scaffold (
        
        topBar = {
            TopAppBar(
                title = { Text("Register") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 🧩 Your entire form content here (as is, reused from your current Column)
                // For brevity, not repeating — all your `OutlinedTextField`s go here
                // ...
                // Scrollable Form Content

                // Full Name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = viewModel::updateFullName,
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = fullNameError != null,
                    supportingText = fullNameError?.let {
                        {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Mobile Number
                OutlinedTextField(
                    value = mobile,
                    onValueChange = viewModel::updateMobile,
                    label = { Text("Mobile Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = mobileError != null,
                    supportingText = mobileError?.let {
                        {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("1234567890") }
                )

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::updateEmail,
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = emailError != null,
                    supportingText = emailError?.let {
                        {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("user@example.com") }
                )

                // Street Address
                OutlinedTextField(
                    value = street,
                    onValueChange = viewModel::updateStreet,
                    label = { Text("Street Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = streetError != null,
                    supportingText = streetError?.let {
                        {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // City & State Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = viewModel::updateCity,
                        label = { Text("City") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        isError = cityError != null,
                        supportingText = cityError?.let {
                            {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 10.sp
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = state,
                        onValueChange = viewModel::updateState,
                        label = { Text("State") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        isError = stateError != null,
                        supportingText = stateError?.let {
                            {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 10.sp
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Pincode & Country Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = pincode,
                        onValueChange = viewModel::updatePincode,
                        label = { Text("Pincode") },
                        modifier = Modifier.weight(0.6f),
                        singleLine = true,
                        isError = pincodeError != null,
                        supportingText = pincodeError?.let {
                            {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 10.sp
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text("400022") }
                    )

                    OutlinedTextField(
                        value = country,
                        onValueChange = viewModel::updateCountry,
                        label = { Text("Country") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        isError = countryError != null,
                        supportingText = countryError?.let {
                            {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 10.sp
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Gender Selection
                Column {
                    Text(
                        text = "Gender",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (genderError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectableGroup(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        viewModel.genderOptions.forEach { option ->
                            val isSelected = selectedGender == option
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .selectable(
                                        selected = isSelected,
                                        onClick = { viewModel.updateGender(option) },
                                        role = Role.RadioButton
                                    )
                                    .background(
                                        color = if (isSelected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = option,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    genderError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Date of Birth
                OutlinedTextField(
                    value = TextFieldValue(
                        text = formatDOB(dobRaw),
                        selection = TextRange(formatDOB(dobRaw).length)
                    ),
                    onValueChange = { input ->
                        viewModel.updateDOB(input.text)
                    },
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = dobError != null,
                    supportingText = dobError?.let {
                        {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("01-01-1990") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar"
                        )
                    }
                )

                // Occupation
                OutlinedTextField(
                    value = occupation,
                    onValueChange = viewModel::updateOccupation,
                    label = { Text("Occupation") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = occupationError != null,
                    supportingText = occupationError?.let {
                        {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Company
                OutlinedTextField(
                    value = company,
                    onValueChange = viewModel::updateCompany,
                    label = { Text("Company") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = companyError != null,
                    supportingText = companyError?.let {
                        {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Marital Status
                Column {
                    Text(
                        text = "Marital Status",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    viewModel.maritalOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = maritalStatus.contains(option),
                                onCheckedChange = { checked ->
                                    viewModel.updateMaritalStatus(option, checked)
                                }
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Register Button
                Button(
                    onClick = {
                        viewModel.register()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = isFormValid && !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Register",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }




                Spacer(modifier = Modifier.height(32.dp))
            }

        }

        if (isLoading) {


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Creating account...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }


        }
    }

}



@Preview(showBackground = true, name = "Register Content - Default")
@Composable
private fun RegisterScreenPreview() {
    val previewViewModel = remember { PreviewRegisterViewModel() }
    MaterialTheme {
       RegisterScreen(
           viewModel = previewViewModel, // ✅ use your fake ViewModel here
           onBackClick = { },
           onRegisterSuccess = {}
       )
    }
}