package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components



import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.components.divider.CustomDivider


// ✅ RECEIPT TOTALS
@Composable
fun ReceiptTotals(receiptData: ReceiptData) {
    Column {
        // Subtotal
        ReceiptTotalRow(
            label = "Subtotal:",
            amount = receiptData.subtotal,
            isTotal = false
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Tax
        ReceiptTotalRow(
            label = "Tax (8%):",
            amount = receiptData.tax,
            isTotal = false
        )

        Spacer(modifier = Modifier.height(8.dp))
        CustomDivider()
        Spacer(modifier = Modifier.height(8.dp))

        // Total
        ReceiptTotalRow(
            label = "TOTAL:",
            amount = receiptData.total,
            isTotal = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReceiptTotals() {


   val receiptData = ReceiptData(
        storeName = "",
        storeAddress = "",
        storePhone = "",
        receiptNumber = "RCP12345678",
        date = "Jun 24, 2025 10:15",
        items = emptyList(),
        subtotal = 0.0,
        tax = 0.0,
        total = 0.0,
        itemCount = 3
    )

    ReceiptTotals(receiptData = receiptData)

}