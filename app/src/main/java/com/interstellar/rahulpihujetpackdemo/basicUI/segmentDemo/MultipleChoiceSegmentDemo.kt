package com.interstellar.rahulpihujetpackdemo.basicUI.segmentDemo


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import com.interstellar.rahulpihujetpackdemo.ui.components.MultipleChoiceSegment

@Composable
fun MultipleChoiceSegmentDemo() {
    var selected by remember { mutableStateOf("Option A") }
    val options = listOf("Option A", "Option B", "Option C")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MultipleChoiceSegment(
            options = options,
            selectedOption = selected,
            onOptionSelected = { selected = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("You selected: $selected", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSegmentDemo() {
    MaterialTheme {
        MultipleChoiceSegmentDemo()
    }
}