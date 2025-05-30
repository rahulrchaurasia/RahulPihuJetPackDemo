package com.interstellar.rahulpihujetpackdemo.basicUI


import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteDropdownDemo() {
    val animals = listOf(
        "Lion", "Tiger", "Leopard", "Cheetah", "Giraffe", "Elephant",
        "Zebra", "Kangaroo", "Koala", "Panda", "Gorilla", "Hippopotamus",
        "Rhinoceros", "Orangutan", "Polar Bear", "Grizzly Bear", "Sloth"
    )

    var query by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val filteredList = remember(query) {
        if (query.isEmpty()) animals else animals.filter {
            it.contains(query, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Animals", fontSize = 16.sp, fontWeight = FontWeight.Medium)

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    isExpanded = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        textFieldSize = it.size.toSize()
                    }
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
                placeholder = { Text("Enter any Animal Name") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )

            AnimatedVisibility(visible = isExpanded && filteredList.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                        items(filteredList) { animal ->
                            DropdownMenuItem(
                                text = { Text(animal) },
                                onClick = {
                                    query = animal
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAutoCompleteDropdownDemo() {
    MaterialTheme {
        AutoCompleteDropdownDemo()
    }
}
