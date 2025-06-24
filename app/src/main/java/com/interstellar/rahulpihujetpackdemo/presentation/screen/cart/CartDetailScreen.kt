@file:OptIn(ExperimentalMaterial3Api::class)

package com.interstellar.rahulpihujetpackdemo.presentation.screen.cart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


import androidx.compose.ui.tooling.preview.Preview

import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem
import com.interstellar.rahulpihujetpackdemo.presentation.components.dialog.CommonAlertDialog

// 3. NEW CartDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailScreen(
    onGoHome: () -> Unit,
    onBackToProducts: () -> Unit,
    onShareProduct: (String) -> Unit,
    onNavigateToProductDetail: (String) -> Unit,
    onNavigateToRecieptScreen: () -> Unit,
    appDataManager: AppDataManager
) {
    // Get all cart items with proper state collection
    val cartItems by appDataManager.cartItems.collectAsState()
    val cartTotal by appDataManager.cartTotal.collectAsState()

    // Calculate proper counts
    val totalUniqueItems = cartItems.size // Number of different products
    val totalQuantity = cartItems.sumOf { it.quantity } // Total quantity of all items

    // Dialog states
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<CartItem?>(null) }
    var showClearCartDialog by remember { mutableStateOf(false) }

    // ✅ CORRECT: Using onBackToProducts for back button
    BackHandler(enabled = true) {
        onBackToProducts() // ✅ Same as topbar back arrow
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Shopping Cart",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "($totalUniqueItems ${if (totalUniqueItems == 1) "item" else "items"})",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackToProducts) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Clear cart action
                    if (cartItems.isNotEmpty()) {
                        IconButton(
                            onClick = { showClearCartDialog = true }
                        ) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear Cart")
                        }
                    }

                    // Share cart action
                    IconButton(
                        onClick = {
                            val shareText = "Check out my cart with $totalUniqueItems items (${totalQuantity} total qty) worth $${String.format("%.2f", cartTotal)}!"
                            onShareProduct(shareText)
                        }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }

                    // Home icon - NEW
                    IconButton(onClick = onGoHome) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                }
            )
        }
    ) { paddingValues ->

        if (cartItems.isEmpty()) {
            // Empty Cart State
            EmptyCartContent(
                onContinueShopping = onGoHome,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            // Main Column Layout - NO BOX, NO FIXED PADDING
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Cart Summary Header - Fixed at top
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Cart Total",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "$${String.format("%.2f", cartTotal)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "$totalQuantity items total",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                        }

                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Scrollable Cart Items List - Takes remaining space
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // THIS IS CRUCIAL - Takes remaining space
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp // Space before bottom button
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = cartItems,
                        key = { it.id }
                    ) { cartItem ->
                        CartItemCard(
                            cartItem = cartItem,
                            onItemClick = {
                                onNavigateToProductDetail(cartItem.id)
                            },
                            onDeleteClick = {
                                itemToDelete = cartItem
                                showDeleteDialog = true
                            },
                            onQuantityChange = { newQuantity ->
                                if (newQuantity <= 0) {
                                    itemToDelete = cartItem
                                    showDeleteDialog = true
                                } else {
                                    appDataManager.updateCartItemQuantity(cartItem.id, newQuantity)
                                }
                            }
                        )
                    }
                }

                // Bottom Button - Fixed at bottom, wrap content height
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onNavigateToRecieptScreen() },
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Icon(Icons.Default.Payment, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Proceed to Checkout")
                    }


                }
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog && itemToDelete != null) {
        CommonAlertDialog(
            showDialog = showDeleteDialog,
            onDismiss = {
                showDeleteDialog = false
                itemToDelete = null
            },
            title = "Remove Item",
            message = "Are you sure you want to remove \"${itemToDelete?.name}\" from your cart?",
            confirmButtonText = "Remove",
            cancelButtonText = "Cancel",
            onConfirm = {
                itemToDelete?.let { appDataManager.removeFromCart(it.id) }
                showDeleteDialog = false
                itemToDelete = null
            },
            icon = Icons.Default.Delete
        )
    }

    // Clear Cart Confirmation Dialog
    if (showClearCartDialog) {
        CommonAlertDialog(
            showDialog = showClearCartDialog,
            onDismiss = { showClearCartDialog = false },
            title = "Clear Cart",
            message = "Are you sure you want to remove all $totalUniqueItems items ($totalQuantity total quantity) from your cart?",
            confirmButtonText = "Clear All",
            cancelButtonText = "Cancel",
            onConfirm = {
                appDataManager.clearCart()
                showClearCartDialog = false
            },
            icon = Icons.Default.DeleteSweep
        )
    }
}

// Preview for CartDetailScreen with items
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Cart Detail - With Items", showSystemUi = true)
@Composable
fun CartDetailScreenWithItemsPreview() {
    MaterialTheme {
        // Note: This is a simplified preview since we can't easily mock AppDataManager
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Shopping Cart (3 items)", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear Cart")
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                // Cart Summary Header
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Cart Total",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "$3,747.00",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Sample cart items
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(3) { index ->
                        val sampleItems = listOf(
                            CartItem("1", "iPhone 15 Pro", "$999", 999.0, 2, "Phones"),
                            CartItem("2", "MacBook Pro", "$2499", 2499.0, 1, "Laptops"),
                            CartItem("3", "AirPods Pro", "$249", 249.0, 1, "Audio")
                        )

                        CartItemCard(
                            cartItem = sampleItems[index],
                            onItemClick = {},
                            onDeleteClick = {}
                        )
                    }
                }

                // Bottom buttons
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Payment, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Proceed to Checkout")
                        }
                    }
                }
            }
        }
    }
}

