package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components


import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.util.ReceiptUtils.shareReceipt



// ✅ RECEIPT INFO
@Composable
fun ReceiptInfo(receiptData: ReceiptData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Receipt #: ${receiptData.receiptNumber}",
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "Date: ${receiptData.date}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Text(
            text = "${receiptData.itemCount} items",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReceiptInfo() {
    ReceiptInfo(
        receiptData = ReceiptData(
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
    )
}
