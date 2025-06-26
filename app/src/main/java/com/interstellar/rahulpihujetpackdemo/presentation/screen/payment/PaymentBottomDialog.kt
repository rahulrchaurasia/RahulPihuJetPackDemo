package com.interstellar.rahulpihujetpackdemo.presentation.screen.payment


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
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.interstellar.rahulpihujetpackdemo.presentation.theme.AppColors
import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.PaymentViewModel

// PaymentBottomDialog.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentBottomDialog(
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onPaymentSuccess: (String) -> Unit
) {
    val selectedPaymentMethod by paymentViewModel.selectedPaymentMethod.collectAsState()
    val cardNumber by paymentViewModel.cardNumber.collectAsState()
    val expiryDate by paymentViewModel.expiryDate.collectAsState()
    val cvv by paymentViewModel.cvv.collectAsState()
    val cardHolderName by paymentViewModel.cardHolderName.collectAsState()
    val isProcessing by paymentViewModel.isProcessing.collectAsState()

    val receiptData = paymentViewModel.generateReceiptData()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    // Handle payment results
    LaunchedEffect(Unit) {
        paymentViewModel.paymentSuccess.collect { transactionId ->
            onPaymentSuccess(transactionId)
        }
    }

    LaunchedEffect(Unit) {
        paymentViewModel.paymentError.collect { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    val paymentMethods = listOf(
        "Credit Card" to Icons.Default.CreditCard,
        "Debit Card" to Icons.Default.Payment,
        "UPI" to Icons.Default.AccountBalance,
        "Cash" to Icons.Default.Money
    )

    ModalBottomSheet(
        onDismissRequest = {
            paymentViewModel.resetPaymentForm()
            onDismiss()
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = Modifier.fillMaxHeight(0.6f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(10.dp)
                    .background(

                        color = Color(0xFFB3E5FC),
                       shape =  RoundedCornerShape(12.dp)
                    )
            )
        }
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
                .background(AppColors.redBackground)
                .padding(vertical = 12.dp)
        ){
// Title
            Text(
                text = "Payment",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
                    .background(AppColors.redBackground),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)

                .navigationBarsPadding(),
            contentPadding = PaddingValues(top = 10.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {


            item {
                Spacer(modifier = Modifier.height(8.dp))
                // Order Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.cardGray
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Order Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Items count and subtotal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Items (${receiptData.itemCount})",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$${String.format("%.2f", receiptData.subtotal)}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Tax
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Tax (8%)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$${String.format("%.2f", receiptData.tax)}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Total
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$${String.format("%.2f", receiptData.total)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            item {
                // Payment Methods Section
                Text(
                    text = "Select Payment Method",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(paymentMethods) { (method, icon) ->
                        PaymentMethodCard(
                            method = method,
                            icon = icon,
                            isSelected = selectedPaymentMethod == method,
                            onClick = { paymentViewModel.updateSelectedPaymentMethod(method) }
                        )
                    }
                }
            }

            // Payment Form (only show for card payments)
            if (selectedPaymentMethod in listOf("Credit Card", "Debit Card")) {
                item {
                    PaymentForm(
                        cardNumber = cardNumber,
                        onCardNumberChange = paymentViewModel::updateCardNumber,
                        expiryDate = expiryDate,
                        onExpiryDateChange = paymentViewModel::updateExpiryDate,
                        cvv = cvv,
                        onCvvChange = paymentViewModel::updateCvv,
                        cardHolderName = cardHolderName,
                        onCardHolderNameChange = paymentViewModel::updateCardHolderName
                    )
                }
            }

            item {
                // Payment Button
                Button(
                    onClick = { paymentViewModel.processPayment() },
                    enabled = !isProcessing && receiptData.total > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Processing...",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Payment,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Pay $${String.format("%.2f", receiptData.total)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            item {
                // Cancel Button
                TextButton(
                    onClick = {
                        paymentViewModel.resetPaymentForm()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isProcessing
                ) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview_PaymentBottomDialog() {
    var showSheet by remember { mutableStateOf(true) }

    MaterialTheme {
        if (showSheet) {
            PaymentBottomDialog(
                onDismiss = { showSheet = false },
                onPaymentSuccess = {
                    showSheet = false
                    println("✅ Payment succeeded with transaction: $it")
                }
            )
        }
    }
}
