package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components


import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem




// ✅ RECEIPT ITEM ROW
@Composable
fun ReceiptItemRow(item: CartItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(2f)
        ) {
            Text(
                text = item.name,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            if (item.category.isNotBlank()) {
                Text(
                    text = item.category,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Text(
            text = "${item.quantity}",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.5f)
        )

        Text(
            text = "$${String.format("%.2f", item.priceValue)}",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "$${String.format("%.2f", item.priceValue * item.quantity)}",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewReceiptItemRow() {
    val cartItem =  CartItem("1", "Beverage", "$999", 999.0, 2, "Phones")
    ReceiptItemRow(item = cartItem)

}