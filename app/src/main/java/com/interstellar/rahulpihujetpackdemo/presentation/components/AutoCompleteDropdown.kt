package com.interstellar.rahulpihujetpackdemo.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

// Reusable AutoComplete Dropdown Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AutoCompleteDropdown(
    label: String,
    placeholder: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T?) -> Unit,
    itemToString: (T) -> String,
    itemToSearchableString: (T) -> String = itemToString,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    maxDropdownHeight: Dp = 200.dp,
    onQueryChanged: ((String) -> Unit)? = null,
    dropdownItemContent: @Composable (T) -> Unit = { item ->
        Text(
            text = itemToString(item),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
) {
    var query by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Update query when selectedItem changes
    LaunchedEffect(selectedItem) {
        query = selectedItem?.let { itemToString(it) } ?: ""
    }

    val filteredList = remember(query, items) {
        if (query.isEmpty()) {
            items
        } else {
            items.filter { item ->
                itemToSearchableString(item).contains(query, ignoreCase = true)
            }
        }
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(

                readOnly = selectedItem != null,
                shape = RoundedCornerShape(10.dp),

                value = query,
                onValueChange = { newValue ->
                    query = newValue
                    onQueryChanged?.invoke(newValue)
                    isExpanded = true
                    if (selectedItem != null && itemToString(selectedItem) != newValue) {
                        onItemSelected(null)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    }
                    .border(
                        width = if (isError) 2.dp else 1.dp,
                        color = if (isError) MaterialTheme.colorScheme.error else Color.Gray,
                        shape = RoundedCornerShape(10.dp)
                    ),


                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = if (isError) MaterialTheme.colorScheme.error else Color.Black,
                    errorIndicatorColor = Color.Transparent, // 🔥 Important to remove red underline
                    disabledIndicatorColor = Color.Transparent,

                ),
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isExpanded = false
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                enabled = enabled,
                isError = isError,
                trailingIcon = {
                    Row {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = {
                                query = ""
                                onItemSelected(null)
                                isExpanded = false
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = if (isError) MaterialTheme.colorScheme.error else Color.Gray
                                )
                            }
                        }
                        IconButton(
                            onClick = { isExpanded = !isExpanded },
                            enabled = enabled
                        ) {
                            Icon(
                                imageVector = if (isExpanded) Icons.Rounded.KeyboardArrowUp
                                else Icons.Rounded.KeyboardArrowDown,
                                contentDescription = if (isExpanded) "Collapse" else "Expand",
                                tint = if (isError) MaterialTheme.colorScheme.error else Color.Black
                            )
                        }
                    }
                }
            )

            if (isError && errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            AnimatedVisibility(
                visible = isExpanded && filteredList.isNotEmpty() && enabled
            ) {
                Card(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = maxDropdownHeight)
                    ) {
                        items(filteredList) { item ->
                            DropdownMenuItem(
                                text = { dropdownItemContent(item) },
                                onClick = {
                                    onItemSelected(item)
                                    query = itemToString(item)
                                    isExpanded = false
                                    keyboardController?.hide()
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
