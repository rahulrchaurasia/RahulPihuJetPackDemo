package com.interstellar.rahulpihujetpackdemo.presentation.components.OutlineTextField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.basicUI.BottomSheetContent
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme

enum class InputFilter {
    NUMBERS_ONLY,           // 0-9
    LETTERS_ONLY,           // A-Z, a-z
    ALPHANUMERIC,           // A-Z, a-z, 0-9
    EMAIL,                  // Email characters
    PHONE,                  // Phone number characters
    NO_SPACES,              // No whitespace
    CUSTOM                  // Use custom regex
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    // Custom filtering parameters
    inputFilter: InputFilter = InputFilter.CUSTOM,
    customRegex: Regex? = null,
    maxLength: Int? = null,
    allowEmpty: Boolean = true,
    transformInput: ((String) -> String)? = null,
    onInvalidInput: ((String) -> Unit)? = null
) {
    // Get the appropriate regex based on filter type
    val filterRegex = remember(inputFilter, customRegex) {
        when (inputFilter) {
            InputFilter.NUMBERS_ONLY -> Regex("^[0-9]*$")
            InputFilter.LETTERS_ONLY -> Regex("^[A-Za-z]*$")
            InputFilter.ALPHANUMERIC -> Regex("^[A-Za-z0-9]*$")
            InputFilter.EMAIL -> Regex("^[A-Za-z0-9@._-]*$")
            InputFilter.PHONE -> Regex("^[0-9\\+\\(\\)\\-\\s]*$")
            InputFilter.NO_SPACES -> Regex("^\\S*$")
            InputFilter.CUSTOM -> customRegex ?: Regex(".*") // Allow all if no custom regex
        }
    }

    // Get appropriate keyboard type
    val keyboardType = remember(inputFilter, keyboardOptions) {
        if (keyboardOptions.keyboardType != KeyboardType.Unspecified) {
            keyboardOptions.keyboardType
        } else {
            when (inputFilter) {
                InputFilter.NUMBERS_ONLY, InputFilter.PHONE -> KeyboardType.Number
                InputFilter.EMAIL -> KeyboardType.Email
                else -> KeyboardType.Text
            }
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            var processedInput = input

            // Apply input transformation first
            transformInput?.let { transform ->
                processedInput = transform(processedInput)
            }

            // Check max length
            if (maxLength != null && processedInput.length > maxLength) {
                onInvalidInput?.invoke("Max length exceeded: $maxLength")
                return@OutlinedTextField
            }

            // Check if input matches filter
            val isValid = if (allowEmpty && processedInput.isEmpty()) {
                true
            } else {
                filterRegex.matches(processedInput)
            }

            if (isValid) {
                onValueChange(processedInput)
            } else {
                onInvalidInput?.invoke("Invalid input: $processedInput")
            }
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions.copy(keyboardType = keyboardType),
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        shape = shape,
        colors = colors
    )
}

// Helper composables for common use cases
@Composable
fun NumberOnlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
    onInvalidInput: ((String) -> Unit)? = null
) {
    FilteredOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        inputFilter = InputFilter.NUMBERS_ONLY,
        maxLength = maxLength,
        onInvalidInput = onInvalidInput
    )
}

@Composable
fun LettersOnlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    transformToUppercase: Boolean = false,
    onInvalidInput: ((String) -> Unit)? = null
) {
    FilteredOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        inputFilter = InputFilter.LETTERS_ONLY,
        transformInput = if (transformToUppercase) { input -> input.uppercase() } else null,
        onInvalidInput = onInvalidInput
    )
}

@Composable
fun AlphanumericTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
    onInvalidInput: ((String) -> Unit)? = null
) {
    FilteredOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        inputFilter = InputFilter.ALPHANUMERIC,
        maxLength = maxLength,
        onInvalidInput = onInvalidInput
    )
}

// Usage Examples

@Composable
fun UsageExamples() {
    var numberInput by remember { mutableStateOf("") }
    var letterInput by remember { mutableStateOf("") }
    var alphanumericInput by remember { mutableStateOf("") }
    var customInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

   // Note : WindowInsets.systemBars includes both status bar (top) and navigation bar (bottom).
    val insets = WindowInsets.systemBars.asPaddingValues()
    Column(modifier = Modifier.fillMaxSize()
        .padding(insets)
        .padding(16.dp)
    ) {
        // Numbers only
        NumberOnlyTextField(
            value = numberInput,
            onValueChange = { numberInput = it },
            label = { Text("Numbers Only") },
            maxLength = 10
        )

        // Letters only (uppercase)
        LettersOnlyTextField(
            value = letterInput,
            onValueChange = { letterInput = it },
            label = { Text("Letters Only (Uppercase)") },
            transformToUppercase = true
        )

        // Alphanumeric with max length
        AlphanumericTextField(
            value = alphanumericInput,
            onValueChange = { alphanumericInput = it },
            label = { Text("Alphanumeric (Max 15)") },
            maxLength = 15,
            onInvalidInput = { errorMessage = it }
        )

        // Custom regex - only allows specific pattern
        FilteredOutlinedTextField(
            value = customInput,
            onValueChange = { customInput = it },
            label = { Text("Custom: A-Z + Numbers + Dash") },
            inputFilter = InputFilter.CUSTOM,
            customRegex = Regex("^[A-Z0-9-]*$"),
            transformInput = { it.uppercase() },
            onInvalidInput = { errorMessage = "Only A-Z, 0-9, and - allowed" }
        )

        // Show error message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun PreviewUsageExamples(){
    RahulPihuJetPackDemoTheme {
        UsageExamples(

        )
    }
}