package com.interstellar.rahulpihujetpackdemo.basicUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier  // ✅ Required for padding, fillMaxSize, etc.
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.ui.components.LabeledCheckbox

import com.interstellar.rahulpihujetpackdemo.ui.theme.RahulPihuJetPackDemoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckboxScreen() {
    // State to track main checkbox
    var isChecked by remember { mutableStateOf(false) }

    // Separate state for labeled checkbox
    var isSubscribed by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkbox Demo") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Default checkbox with label that updates text
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (isChecked) "Checked" else "Unchecked",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Reusable labeled checkbox component
            LabeledCheckbox(
                label = "Subscribe to newsletter",
                isChecked = isSubscribed,
                onCheckedChange = { isSubscribed = it }
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun CheckboxScreenPreview() {
    RahulPihuJetPackDemoTheme {
        CheckboxScreen()
    }
}
