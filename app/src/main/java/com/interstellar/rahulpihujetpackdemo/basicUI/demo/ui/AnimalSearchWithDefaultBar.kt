package com.interstellar.rahulpihujetpackdemo.basicUI.demo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AnimalSearchWithDefaultBar() {
    val animals = listOf(
        "Lion", "Tiger", "Elephant", "Zebra", "Giraffe",
        "Koala", "Panda", "Kangaroo", "Cheetah", "Leopard"
    )
    var query by rememberSaveable { mutableStateOf("") }
    val filteredAnimals = remember(query) {
        if (query.isBlank()) animals
        else animals.filter { it.contains(query, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { /* Optional search action */ },
            active = true,
            onActiveChange = {},
            placeholder = { Text("Search animals...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredAnimals) { item ->
                    ListItem(
                        headlineContent = { Text(text = item) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { query = item }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimalSearchWithDefaultBar() {
    MaterialTheme {
        AnimalSearchWithDefaultBar()
    }
}
