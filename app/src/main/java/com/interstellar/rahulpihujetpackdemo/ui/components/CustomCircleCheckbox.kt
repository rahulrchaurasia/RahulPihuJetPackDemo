package com.interstellar.rahulpihujetpackdemo.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomCircleCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    size: Int = 24,
    checkedColor: Color = Color.Red,
    borderColor: Color = Color.Gray
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(if (isChecked) checkedColor else Color.Transparent)
            .clickable { onCheckedChange(!isChecked) }
            .border(width = 2.dp, color = borderColor, shape = CircleShape)
    )
}