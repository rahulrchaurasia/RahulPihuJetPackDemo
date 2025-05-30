package com.interstellar.rahulpihujetpackdemo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.domain.model.Animal

@Composable
fun AnimalSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    animals: List<Animal>,
    onSuggestionClicked: (Animal) -> Unit,
    placeholder: String = "Search...",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            placeholder = { Text(placeholder) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        val filtered = animals.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.species.contains(query, ignoreCase = true)
        }

        if (query.isNotBlank()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .heightIn(max = 250.dp)
            ) {
                items(filtered, key = { it.id }) { animal ->
                    ListItem(
                        headlineContent = { Text(animal.name) },
                        supportingContent = { Text(animal.species) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSuggestionClicked(animal)
                            }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
