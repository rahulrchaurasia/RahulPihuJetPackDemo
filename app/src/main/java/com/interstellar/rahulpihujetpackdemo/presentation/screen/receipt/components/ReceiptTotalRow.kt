package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components


import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Share
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
import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData

import com.interstellar.rahulpihujetpackdemo.presentation.util.ReceiptUtils.shareReceipt

import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.ReceiptViewModel
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions


// ✅ RECEIPT TOTAL ROW
@Composable
fun ReceiptTotalRow(
    label: String,
    amount: Double,
    isTotal: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = if (isTotal) 18.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = Color.Black
        )

        Text(
            text = "$${String.format("%.2f", amount)}",
            fontSize = if (isTotal) 18.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = Color.Black
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewReceiptTotalRow() {

    ReceiptTotalRow(label = "Subtotal:", amount = 100.0, isTotal = false)


}