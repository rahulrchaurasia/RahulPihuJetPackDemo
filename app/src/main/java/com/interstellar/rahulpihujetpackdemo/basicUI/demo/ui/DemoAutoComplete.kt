package com.interstellar.rahulpihujetpackdemo.basicUI.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.interstellar.rahulpihujetpackdemo.domain.model.Animal


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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.interstellar.rahulpihujetpackdemo.domain.model.ValidationResult
import com.interstellar.rahulpihujetpackdemo.presentation.components.AutoCompleteDropdown

@Composable
fun DemoAutoComplete() {
    val animals = remember {
        listOf(
            Animal(1, "Lion", "Panthera leo", "Savanna", "Carnivore", "12-16 years"),
            Animal(2, "Tiger", "Panthera tigris", "Forest", "Carnivore", "20-25 years"),
            Animal(3, "Elephant", "Elephantidae", "Savanna/Forest", "Herbivore", "60-80 years"),
            Animal(4, "Giraffe", "Giraffa", "Savanna", "Herbivore", "20-25 years"),
            Animal(5, "Lepord", "Equus", "Grasslands", "Herbivore", "25-30 years"),
            Animal(6, "Kangaroo", "Macropus", "Grasslands", "Herbivore", "15-20 years"),
            Animal(7, "Koala", "Phascolarctos cinereus", "Forest", "Herbivore", "15-20 years"),
            Animal(
                8,
                "Panda",
                "Ailuropoda melanoleuca",
                "Bamboo Forest",
                "Herbivore",
                "15-20 years"
            ),
            Animal(9, "Gorilla", "Gorilla", "Forest", "Herbivore", "30-40 years"),
            Animal(10, "Polar Bear", "Ursus maritimus", "Arctic", "Carnivore", "25-30 years")
        )
    }

    var selectedAnimal by remember { mutableStateOf<Animal?>(null) }
    var validationResult by remember { mutableStateOf<ValidationResult>(ValidationResult.Success) }
    var showDetails by remember { mutableStateOf(false) }

    fun validateSelection(): ValidationResult {
        return when {
            selectedAnimal == null -> ValidationResult.Error("Please select an animal from the dropdown")
            else -> ValidationResult.Success
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Animal Selection Demo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        AutoCompleteDropdown(
            label = "Select Animal",
            placeholder = "Enter or select an animal name",
            items = animals,
            selectedItem = selectedAnimal,
            onItemSelected = { animal ->
                selectedAnimal = animal
                validationResult = ValidationResult.Success // Clear error on selection
                showDetails = false // Hide details when selection changes
            },
            itemToString = { it.name },
            itemToSearchableString = { "${it.name} ${it.species}" },
            isError = validationResult is ValidationResult.Error,
            errorMessage = (validationResult as? ValidationResult.Error)?.message ?: "",
            modifier = Modifier.fillMaxWidth(),
            dropdownItemContent = { animal ->
                Column {
                    Text(
                        text = animal.name,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = animal.species,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        )

        Button(
            onClick = {
                validationResult = validateSelection()
                if (validationResult is ValidationResult.Success) {
                    showDetails = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        ) {
            Text("Submit Selection")
        }

        // Display selected animal details
        if (showDetails && selectedAnimal != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Selected Animal Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    HorizontalDivider()

                    DetailRow("ID", selectedAnimal!!.id.toString())
                    DetailRow("Name", selectedAnimal!!.name)
                    DetailRow("Species", selectedAnimal!!.species)
                    DetailRow("Habitat", selectedAnimal!!.habitat)
                    DetailRow("Diet", selectedAnimal!!.diet)
                    DetailRow("Average Lifespan", selectedAnimal!!.averageLifespan)
                }
            }
        }

        // Reset button
        if (selectedAnimal != null || showDetails) {
            OutlinedButton(
                onClick = {
                    selectedAnimal = null
                    validationResult = ValidationResult.Success
                    showDetails = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset Selection")
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun PreviewDemoAutoComplete() {
    MaterialTheme {
        DemoAutoComplete()
    }
}