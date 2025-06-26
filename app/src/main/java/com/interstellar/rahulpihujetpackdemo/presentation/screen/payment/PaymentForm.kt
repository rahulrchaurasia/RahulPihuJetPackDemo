package com.interstellar.rahulpihujetpackdemo.presentation.screen.payment

import androidx.compose.runtime.Composable

// Compose UI
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

// Other
import kotlinx.coroutines.flow.collect
import android.widget.Toast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import com.interstellar.rahulpihujetpackdemo.presentation.theme.AppColors
import com.interstellar.rahulpihujetpackdemo.presentation.util.formatCardNumber
import com.interstellar.rahulpihujetpackdemo.presentation.util.formatExpiry

@Composable
fun PaymentForm(
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    expiryDate: String,
    onExpiryDateChange: (String) -> Unit,
    cvv: String,
    onCvvChange: (String) -> Unit,
    cardHolderName: String,
    onCardHolderNameChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    // For cursor control in card number and expiry
    var cardRaw by remember { mutableStateOf(cardNumber.filter { it.isDigit() }) }
    var expiryRaw by remember { mutableStateOf(expiryDate.filter { it.isDigit() }) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =  AppColors.cardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Card Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            // Card Holder Name
            OutlinedTextField(
                value = cardHolderName,
                onValueChange = onCardHolderNameChange,
                label = { Text("Card Holder Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(12.dp)
            )
            
            // Card Number
            OutlinedTextField(
                value = TextFieldValue(
                    text = formatCardNumber(cardRaw),
                    selection = TextRange(formatCardNumber(cardRaw).length)
                ),
                onValueChange = { input ->
                    val digits = input.text.filter { it.isDigit() }
                    if (digits.length <= 16) {
                        cardRaw = digits
                        onCardNumberChange(formatCardNumber(digits))
                    }
                },
                label = { Text("Card Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("1234 5678 9012 3456") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
            
            // Expiry Date and CVV
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = TextFieldValue(
                        text = formatExpiry(expiryRaw),
                        selection = TextRange(formatExpiry(expiryRaw).length)
                    ),
                    onValueChange = { input ->
                        val digits = input.text.filter { it.isDigit() }
                        if (digits.length <= 4) {
                            expiryRaw = digits
                            onExpiryDateChange(formatExpiry(digits))
                        }
                    },
                    label = { Text("MM/YY") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("12/25") }
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }
                        if (digits.length <= 3) onCvvChange(digits)
                    },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("123") },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Cart Item Card")
@Composable
fun PreviewPaymentForm() {
    MaterialTheme {
        PaymentForm(cardNumber = "CP3490-WE", onCardNumberChange = {},
            expiryDate = "10/2028",
            onExpiryDateChange = {},
            cvv = "3348", onCvvChange = {},
            cardHolderName = "Rakesh Varma",
            onCardHolderNameChange = {}
        )
    }
}