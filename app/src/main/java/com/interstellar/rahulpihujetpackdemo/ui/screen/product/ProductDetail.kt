package com.interstellar.rahulpihujetpackdemo.ui.screen.product

import androidx.activity.compose.BackHandler
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
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager
import com.interstellar.rahulpihujetpackdemo.rootGraph.router.HomeRoutes
import com.interstellar.rahulpihujetpackdemo.ui.components.dialog.CommonAlertDialog

// Serialization (for Product data class)
import kotlinx.serialization.Serializable

// 7. Updated Product Detail Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onBackPressed: () -> Unit,
    appDataManager: AppDataManager, // ✅ Add this parameter,
    onNavigateToCart: () -> Unit = {} // ✅ NEW: Direct cart navigation
) {

    // ✅ Custom back handling for specific screens
//    BackHandler(enabled = true) {
//        onBackPress()
//    }
    // Mock product data based on ID
    val product = remember(productId) {
        sampleProducts.find { it.id == productId } ?: Product(
            id = productId,
            name = "Unknown Product",
            price = "$0",
            category = "Unknown",
            description = "Product not found in our catalog."
        )
    }


    // Cart state
    val cartItems by appDataManager.cartItems.collectAsState()
    val existingCartItem = cartItems.find { it.id == productId }
    val isInCart = existingCartItem != null

    // UI state
   // var quantity by remember { mutableIntStateOf(1) }
    var quantity by remember(existingCartItem?.quantity) {
        mutableIntStateOf(existingCartItem?.quantity ?: 1)
    }
    var isFavorite by remember { mutableStateOf(false) }
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
                    // ✅ FIXED: Reduced spacing between cart and favorite icons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy((2).dp), // ✅ Reduced spacing
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Show cart item count if items exist
                        val cartCount by appDataManager.cartCount.collectAsState()
                        if (cartCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        modifier = Modifier.offset(x = (-8).dp, y = 4.dp) // ✅ Move badge closer to cart icon

                                    ) {
                                        Text(cartCount.toString())
                                    }
                                },

                            ) {
                                IconButton(
                                    onClick = {
                                        // Navigate to cart
                                        onNavigateToCart() // ✅ Use new navigation function
                                    }
                                ) {
                                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                                }
                            }
                        }

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
                }
            )
        },
        bottomBar = {
            // ✅ FIXED Bottom Action Bar - Layout corrected
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {

                    // Show existing cart item info
                    if (isInCart) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Already in cart: ${existingCartItem?.quantity} items",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // ✅ FIXED Row layout - buttons now visible
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // ✅ Wishlist button - now properly sized
                        OutlinedButton(
                            onClick = {
                                // ✅ TODO: Add to wishlist functionality
                                isFavorite = !isFavorite
                            },
                            modifier = Modifier.size(48.dp), // ✅ Fixed size instead of aspectRatio
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Add to Wishlist",
                                modifier = Modifier.size(20.dp),
                                tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.primary
                            )
                        }

                        // ✅ Add space between buttons
                        Spacer(modifier = Modifier.weight(1f))

                        // ✅ Add to Cart button - now properly sized
                        Button(
                            onClick = {
                                showAddToCartDialog = true
                            },
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isInCart) "Add More" else "Add to Cart",
                                fontSize = 14.sp
                            )
                        }
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
                        imageVector = when (product.category) {
                            "Phones" -> Icons.Default.Phone
                            "Laptops" -> Icons.Default.Computer
                            "Tablets" -> Icons.Default.Tablet
                            "Audio" -> Icons.Default.Headphones
                            "Wearables" -> Icons.Default.Watch
                            else -> Icons.Default.ShoppingBag
                        },
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }
            }

            // Product Info
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                // Category Badge
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
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
                            FilledTonalIconButton(
                                onClick = { if (quantity > 1) quantity-- }
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease")
                            }

                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )

                            FilledTonalIconButton(
                                onClick = { quantity++ }
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Features Section
                Text(
                    text = "Features",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

               // val productWithFeatures = products
                val features = when (product.category) {
                    "Phones" -> listOf(
                        "Latest Processor",
                        "High-Resolution Camera",
                        "All-Day Battery Life",
                        "Water Resistant",
                        "Fast Charging"
                    )

                    "Laptops" -> listOf(
                        "Powerful Performance",
                        "High-Resolution Display",
                        "Long Battery Life",
                        "Premium Build Quality",
                        "Fast SSD Storage"
                    )

                    else -> listOf(
                        "Premium Quality Materials",
                        "Latest Technology",
                        "1 Year Warranty",
                        "Free Shipping",
                        "30-Day Return Policy"
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    features.forEach { feature ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = feature,
                                style = MaterialTheme.typography.bodyMedium
                            )
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
        message = buildString {
            append("Add \"${product.name}\" (Qty: $quantity) to your cart for ${product.price}?")
            if (isInCart) {
                append("\n\nThis will add $quantity more items to your existing ${existingCartItem?.quantity} items.")
            }
        },
        confirmButtonText = if (isInCart) "Add More" else "Add to Cart",
        cancelButtonText = "Cancel",
        onConfirm = {
            // Add to cart using AppDataManager
            appDataManager.addToCart(product, quantity)

            // Navigate to cart detail to show added item
            onNavigateToCart()
        },
        icon = Icons.Default.ShoppingCart
    )
}
//@Preview(showBackground = true)
//@Composable
//fun ProductDetailScreenPreview() {
//    MaterialTheme {
//        ProductDetailScreen(
//            productId = "product_1",
//            onAddToCart = { id, name, price ->
//                println("Added to cart: $id, $name, $price")
//            },
//            onBackPressed = {
//                println("Back pressed")
//            },
//            appDataManager = appDataManager
//        )
//    }
//}
