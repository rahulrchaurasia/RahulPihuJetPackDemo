package com.interstellar.rahulpihujetpackdemo.ui.components


// File: ui/components/LabeledCheckbox.kt



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.basicUI.OutlinedCardExample
import com.interstellar.rahulpihujetpackdemo.ui.theme.RahulPihuJetPackDemoTheme
import kotlin.math.truncate

@Composable
fun LabeledCheckbox(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun LabeledCheckboxPreview() {
    RahulPihuJetPackDemoTheme {
        LabeledCheckbox(
            label = "Checkbox",
            isChecked = true,
            onCheckedChange = {}
        )
    }
}
