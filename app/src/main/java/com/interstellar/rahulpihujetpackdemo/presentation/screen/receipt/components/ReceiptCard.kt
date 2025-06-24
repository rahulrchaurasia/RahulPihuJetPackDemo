package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.components.divider.CustomDivider

// ✅ RECEIPT CARD COMPONENT
@Composable
fun ReceiptCard(
    receiptData: ReceiptData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Store Header
            ReceiptHeader(receiptData)

            Spacer(modifier = Modifier.height(16.dp))
            CustomDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // Receipt Info
            ReceiptInfo(receiptData)

            Spacer(modifier = Modifier.height(16.dp))
            CustomDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // Items
            ReceiptItems(receiptData.items)

            Spacer(modifier = Modifier.height(16.dp))
            CustomDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // Totals
            ReceiptTotals(receiptData)

            Spacer(modifier = Modifier.height(20.dp))
            CustomDivider(isDashed = true)
            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            ReceiptFooter()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewReceiptCard() {


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

    ReceiptCard(receiptData = receiptData,modifier = Modifier.padding(16.dp))

}
