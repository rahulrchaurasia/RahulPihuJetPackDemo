package com.interstellar.rahulpihujetpackdemo.ui.screen.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



// Core Compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

// Material Design 3
import androidx.compose.material3.*

// Material Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Tablet
import androidx.compose.material.icons.filled.Watch

// Runtime & State
import androidx.compose.runtime.*

// UI Modifiers & Alignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Window Insets (for safe area)
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding

// Preview (optional)
import androidx.compose.ui.tooling.preview.Preview
import com.interstellar.rahulpihujetpackdemo.data.local.model.Product
import com.interstellar.rahulpihujetpackdemo.ui.components.dialog.CommonAlertDialog

// Serialization (for Product data class)
import kotlinx.serialization.Serializable

// 7. Updated Product Detail Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onAddToCart: (String, String, String) -> Unit,
    onBackPressed: () -> Unit
) {
    // Mock product data based on ID
    val product = remember(productId) {
        Product(
            id = productId,
            name = "Product $productId",
            price = "$${(productId.toIntOrNull() ?: 1) * 100}",
            category = "Electronics",
            description = "This is a detailed description of product $productId with all the amazing features and specifications."
        )
    }

    var quantity by remember { mutableIntStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }

    // ✅ ONLY ADD THIS - Dialog state
    var showAddToCartDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { isFavorite = !isFavorite }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Bottom Action Bar
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        onClick = { /* Add to wishlist */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Wishlist")
                    }

                    Button(
                        onClick = {
                           // onAddToCart(product.id, product.name, product.price)
                            showAddToCartDialog = true
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add to Cart")
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            // Product Image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.Gray
                    )
                }
            }

            // Product Info
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.price,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Quantity Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- }
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease")
                        }

                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        IconButton(
                            onClick = { quantity++ }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Increase")
                        }
                    }
                }
            }
        }
    }

    // ✅ ADD THIS - Alert Dialog
    CommonAlertDialog(
        showDialog = showAddToCartDialog,
        onDismiss = { showAddToCartDialog = false },
        title = "Add to Cart",
        message = "Do you want to add \"${product.name}\" (Qty: $quantity) to your cart for ${product.price}?",
        confirmButtonText = "Add to Cart",
        cancelButtonText = "Cancel",
        onConfirm = {
            onAddToCart(product.id, product.name, product.price)
        },
        icon = Icons.Default.ShoppingCart
    )
}
@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    MaterialTheme {
        ProductDetailScreen(
            productId = "product_1",
            onAddToCart = { id, name, price ->
                println("Added to cart: $id, $name, $price")
            },
            onBackPressed = {
                println("Back pressed")
            }
        )
    }
}
