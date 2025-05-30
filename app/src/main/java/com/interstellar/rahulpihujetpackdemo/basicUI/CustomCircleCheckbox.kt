package com.interstellar.rahulpihujetpackdemo.basicUI

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
import com.interstellar.rahulpihujetpackdemo.ui.components.CustomCircleCheckbox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCheckboxScreen() {
    var isChecked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Custom Checkbox") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomCircleCheckbox(
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = if (isChecked) "Checked" else "Unchecked",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomCheckboxScreenPreview() {
    MaterialTheme {
        CustomCheckboxScreen()
    }
}
