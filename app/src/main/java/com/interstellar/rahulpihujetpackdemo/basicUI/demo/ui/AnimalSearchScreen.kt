package com.interstellar.rahulpihujetpackdemo.basicUI.demo.ui

import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.toSize
import com.interstellar.rahulpihujetpackdemo.domain.model.Animal
import com.interstellar.rahulpihujetpackdemo.presentation.components.AnimalSearchBar
import com.interstellar.rahulpihujetpackdemo.presentation.components.CustomSearchBar


@Composable
fun AnimalSearchScreen() {
    var query by remember { mutableStateOf("") }
    var selectedAnimal by remember { mutableStateOf<Animal?>(null) }

    val animals = listOf(
        Animal(1, "Lion", "Panthera leo", "Savanna", "Carnivore", "12-16 years"),
        Animal(2, "Tiger", "Panthera tigris", "Jungle", "Carnivore", "20-25 years"),
        Animal(3, "Elephant", "Elephantidae", "Forest", "Herbivore", "60-80 years"),
        Animal(4, "Zebra", "Equus quagga", "Grasslands", "Herbivore", "25 years"),
        Animal(5, "Lepord", "Equus", "Grasslands", "Herbivore", "25-30 years"),
        Animal(6, "Kangaroo", "Macropus", "Grasslands", "Herbivore", "15-20 years"),
        Animal(7, "Koala", "Phascolarctos cinereus", "Forest", "Herbivore", "15-20 years"),
        Animal(8,
            "Panda",
            "Ailuropoda melanoleuca",
            "Bamboo Forest",
            "Herbivore",
            "15-20 years"
        ),
        Animal(9, "Gorilla", "Gorilla", "Forest", "Herbivore", "30-40 years"),
        Animal(10, "Polar Bear", "Ursus maritimus", "Arctic", "Carnivore", "25-30 years")

    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

//        AnimalSearchBar(
//            query = query,
//            onQueryChange = { query = it },
//            animals = animalList,
//            onSuggestionClicked = {
//                selectedAnimal = it
//                query = it.name // autofill
//            }
//        )


            CustomSearchBar(
                query = query,
                onQueryChange = { query = it },
                items = animals,
                itemToString = { it.name },
                onItemSelected = {
                    selectedAnimal = it
                    query = it.name
                },
                itemContent = { animal ->
                    Column {
                        Text(animal.name, fontWeight = FontWeight.Bold)
                        Text(animal.species, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }
            )



            Spacer(modifier = Modifier.height(20.dp))


            selectedAnimal?.let { animal ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Selected Animal:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Name: ${animal.name}")
                        Text("Species: ${animal.species}")
                        Text("Habitat: ${animal.habitat}")
                        Text("Diet: ${animal.diet}")
                        Text("Lifespan: ${animal.averageLifespan}")
                    }
                }
            }


        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAnimalSearchScreen() {
    MaterialTheme {
        AnimalSearchScreen()
    }
}
