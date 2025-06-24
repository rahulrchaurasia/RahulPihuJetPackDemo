package com.interstellar.rahulpihujetpackdemo.presentation.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- Generic Custom Search Bar ---
@Composable
fun <T> CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    items: List<T>,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    debounceTime: Long = 300L,
    itemContent: @Composable (T) -> Unit = { item -> Text(text = itemToString(item)) }
) {
    var internalQuery by remember { mutableStateOf(query) }
    var isExpanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    val filteredItems = remember(internalQuery, items) {
        if (internalQuery.isBlank()) items else items.filter {
            itemToString(it).contains(internalQuery, ignoreCase = true)
        }
    }

    Box(modifier = modifier.zIndex(1f)) {
        Column {
            OutlinedTextField(
                value = internalQuery,
                onValueChange = { newValue ->
                    internalQuery = newValue
                    isExpanded = true
                    debounceJob?.cancel()
                    debounceJob = coroutineScope.launch {
                        delay(debounceTime)
                        onQueryChange(newValue)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { textFieldSize = it.size.toSize() },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (internalQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            internalQuery = ""
                            onQueryChange("")
                            isExpanded = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                placeholder = { Text(placeholder) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    isExpanded = false
                })
            )

            AnimatedVisibility(
                visible = isExpanded && filteredItems.isNotEmpty(),
                modifier = Modifier
                    .absoluteOffset(y = with(LocalDensity.current) { textFieldSize.height.toDp() + 4.dp })
                    .zIndex(1f)
            ) {
                Card(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    LazyColumn(modifier = Modifier.heightIn(max = 250.dp)) {
                        items(filteredItems) { item ->
                            ListItem(
                                headlineContent = { itemContent(item) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemSelected(item)
                                        internalQuery = itemToString(item)
                                        isExpanded = false
                                        keyboardController?.hide()
                                    }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}