package com.interstellar.rahulpihujetpackdemo.presentation.screen.payment


// Compose Core
import androidx.compose.foundation.layout.*


// Material 3
import androidx.compose.material3.*

// Runtime
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// UI

import androidx.compose.ui.unit.dp

// Navigation and System

// Hilt

// Coroutines

// If using ModalBottomSheetLayout (Material 2 version)
// Note: Consider migrating to Material 3's BottomSheetScaffold


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentBottomDialog1(
    onDismiss: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedPaymentMethod by remember { mutableStateOf("Credit Card") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Payment",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Example payment method selector
            Text("Selected: $selectedPaymentMethod")
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onPaymentSuccess()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pay Now")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}
