package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember





import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.ReceiptViewModel


// ✅ RECEIPT HEADER
@Composable
fun ReceiptHeader(receiptData: ReceiptData,
                  viewModel: ReceiptViewModel = hiltViewModel(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = receiptData.storeName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Transaction ID: ${viewModel.transactionId.orEmpty()}",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = receiptData.storeAddress,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Text(
            text = receiptData.storePhone,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReceiptHeader() {
    ReceiptHeader(
        receiptData = ReceiptData(
            storeName = "Coffee House",
            storeAddress = "123 Brew Street, Bean City",
            storePhone = "+1 (555) 123-4567",
            receiptNumber = "RCP12345678",
            date = "Jun 24, 2025 10:15",
            items = emptyList(),
            subtotal = 0.0,
            tax = 0.0,
            total = 0.0,
            itemCount = 0
        )
    )
}
