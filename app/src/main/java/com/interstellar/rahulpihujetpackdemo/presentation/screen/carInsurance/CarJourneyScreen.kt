package com.interstellar.rahulpihujetpackdemo.presentation.screen.carInsurance



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarJourneyScreen(
    globalActions: GlobalNavigationActions,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Car Journey") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Welcome to the Car Journey Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCarJourneyScreen() {
    CarJourneyScreen(
        globalActions = GlobalNavigationActions(navController = NavController(LocalContext.current)),
        onBackPressed = {}
    )
}

