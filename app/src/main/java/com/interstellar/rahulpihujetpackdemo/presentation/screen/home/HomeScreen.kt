package com.interstellar.rahulpihujetpackdemo.presentation.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


//Note : Used  windowInsets = WindowInsets(0) : Disable insets

// ✅ SIMPLE child screens - no Scaffold, no safe area handling
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProducts: () -> Unit = {},
    onNavigateToCart: () -> Unit = {}
) {
    // ✅ Simple Column - parent Scaffold handles safe areas
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // ✅ TopAppBar with disabled window insets
        TopAppBar(
            title = { Text("Home") },
            actions = {
                IconButton(onClick = onNavigateToCart) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
            },
            windowInsets = WindowInsets(0) //05 VVIMP  // ✅ CRITICAL: Disable insets
        )

        // ✅ Content area
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "🏠 Home Screen",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Welcome to your home dashboard",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onNavigateToProducts,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Browse Products")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentPreview() {
    MaterialTheme {
        HomeScreen(
            onNavigateToProducts = {},
          //  onNavigateToCart = {}
        )
    }
}