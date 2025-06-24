package com.interstellar.rahulpihujetpackdemo.basicUI

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState



import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
    fun InputChipExample(){


        var isSelected by remember { mutableStateOf(true) }

        InputChip(
            selected = isSelected,
            onClick = { isSelected = !isSelected },
            label = { Text("Input Chip") },
            avatar = {

               Icon(Icons.Filled.Person, contentDescription = "person", modifier = Modifier.size(InputChipDefaults.AvatarSize))
            },
            trailingIcon = {
                Icon(Icons.Filled.Close, contentDescription = "close", modifier = Modifier.size(InputChipDefaults.AvatarSize))

            },
            modifier = Modifier.padding(16.dp).padding(top = 60.dp)
        )
    }


@Composable
fun InputChipWithAvatarAndClick() {
    val context = LocalContext.current
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        InputChip(
            selected = isSelected,
            onClick = {
                isSelected = !isSelected
                Toast.makeText(
                    context,
                    if (isSelected) "Chip Selected" else "Chip Deselected",
                    Toast.LENGTH_SHORT
                ).show()
            },
            label = { Text(text = if (isSelected) "Selected Chip" else "Input Chip") },
            avatar = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "avatar",
                    modifier = Modifier.size(InputChipDefaults.AvatarSize)
                )
            },
            trailingIcon = {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close icon",
                        modifier = Modifier
                            .size(InputChipDefaults.AvatarSize)
                    )
                }
            },
            colors = InputChipDefaults.inputChipColors(
                selectedContainerColor = Color(0xFFE57373),
                selectedLabelColor = Color.White
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun InputChipExamplePreview() {
    RahulPihuJetPackDemoTheme {
        InputChipExample()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun InputChipWithAvatarAndClickPreview() {
    RahulPihuJetPackDemoTheme {
        InputChipWithAvatarAndClick()
    }
}