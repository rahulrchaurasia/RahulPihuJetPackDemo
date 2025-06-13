package com.interstellar.rahulpihujetpackdemo.ui.screen.module


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.interstellar.rahulpihujetpackdemo.rootGraph.router.HomeRoutes


// 5. Updated Tab Content Screens
@Composable
fun HomeTabContent(localNavController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Welcome to ShopApp!",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = {
                localNavController.navigate(HomeRoutes.Products.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Browse Products")
        }

        // Add more home content here
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Featured Products",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Check out our latest arrivals",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun WishListScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = "Wishlist",
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Your Wishlist is Empty",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Save items you love to buy later",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CartScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Your Cart is Empty",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Add items to get started",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "john.doe@example.com",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Profile options
        ProfileOption(title = "Orders", onClick = { })
        ProfileOption(title = "Settings", onClick = { })
        ProfileOption(title = "Help & Support", onClick = { })

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Logout")
        }
    }
}

@Composable
fun ProfileOption(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigate"
            )
        }
    }
}

