package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components


import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem



// ✅ RECEIPT ITEMS
@Composable
fun ReceiptItems(items: List<CartItem>) {
    Column {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ITEM",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "QTY",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = "PRICE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "TOTAL",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Items
        items.forEach { item ->
            ReceiptItemRow(item)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReceiptItems() {
    ReceiptItems(
        items = listOf(
            CartItem("1", "Beverage", "$999", 999.0, 2, "Phones"),
        CartItem("2", "Bakery", "$2499", 2499.0, 1, "Laptops"),
        CartItem("3", "AirPods Pro", "$249", 249.0, 1, "Audio"))
    )

}
