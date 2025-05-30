package com.interstellar.rahulpihujetpackdemo.basicUI.demo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.basicUI.demo.androidView.XmlAutoCompleteBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteComposeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "XML AutoComplete in Compose",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // XML AutoComplete Component
        XmlAutoCompleteBox(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // You can add other Compose components here
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = "This AutoComplete is built using XML and integrated into Compose using AndroidView",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AutoCompleteComposeScreenPreview() {
    MaterialTheme {
        AutoCompleteComposeScreen()
    }
}