package com.interstellar.rahulpihujetpackdemo.presentation.components.divider

import androidx.compose.runtime.Composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp

// ✅ RECEIPT DIVIDER
@Composable
fun CustomDivider(isDashed: Boolean = false) {
    if (isDashed) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        ) {
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
            drawLine(
                color = androidx.compose.ui.graphics.Color.Gray,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect,
                strokeWidth = 2.dp.toPx()
            )
        }
    } else {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}