package com.interstellar.rahulpihujetpackdemo.basicUI.segmentDemo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons


import androidx.compose.material.icons.automirrored.filled.DirectionsWalk

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsWalk


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.Close

import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiChoiceSegmentWithIcons() {
    val options = listOf("Walk", "Ride", "Drive")
    val selectedOptions = remember { mutableStateListOf(false, false, false) }


    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ){

        MultiChoiceSegmentedButtonRow() {
            options.forEachIndexed { index, label ->

                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.count()
                    ),
                    checked = selectedOptions[index],
                    onCheckedChange = {
                        selectedOptions[index] = !selectedOptions[index]
                    },
                    icon = {
                        SegmentedButtonDefaults.Icon(selectedOptions[index])
                    },
                    label = {

                        when (label) {


                            "Walk" -> Icon(
                                Icons.AutoMirrored.Filled.DirectionsWalk,
                                contentDescription = "Direction walk"
                            )

                            "Ride" -> Icon(
                                Icons.Default.DirectionsBus,
                                contentDescription = "Direction walk"
                            )

                            "Drive" -> Icon(
                                Icons.Default.DirectionsCar,
                                contentDescription = "Direction walk"
                            )

                        }
                    }

                )

            }

        }
    }




}
@Preview(showBackground = true)
@Composable
fun MultiChoiceSegmentWithIconsPreview() {
    MaterialTheme {
        MultiChoiceSegmentWithIcons()
    }
}