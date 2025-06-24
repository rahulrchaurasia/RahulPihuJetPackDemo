package com.interstellar.rahulpihujetpackdemo.presentation.screen.cart

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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.ui.tooling.preview.Preview

import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem

// 2. CartItemCard.kt - Individual cart item component
// Enhanced CartItemCard with quantity controls
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItemCard(
    cartItem: CartItem,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onQuantityChange: (Int) -> Unit = {}
) {
    Card(
        onClick = onItemClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Icon
            Surface(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Icon(
                    Icons.Default.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(14.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = cartItem.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cartItem.price,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = " × ${cartItem.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "Total: $${String.format("%.2f", cartItem.price.removePrefix("$").toDouble() * cartItem.quantity)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Quantity Controls & Delete
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Quantity controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = { onQuantityChange(cartItem.quantity - 1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease quantity",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = "${cartItem.quantity}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(
                        onClick = { onQuantityChange(cartItem.quantity + 1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase quantity",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Delete button
                IconButton(
                    onClick = onDeleteClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
// Preview for CartItemCard
@Preview(showBackground = true, name = "Cart Item Card")
@Composable
fun CartItemCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Phone item
            CartItemCard(
                cartItem = CartItem(
                    id = "1",
                    name = "iPhone 15 Pro",
                    price = "$999",
                    priceValue = 999.0,
                    quantity = 2,
                    category = "Phones"
                ),
                onItemClick = {},
                onDeleteClick = {}
            )

            // Laptop item
            CartItemCard(
                cartItem = CartItem(
                    id = "2",
                    name = "MacBook Pro 16-inch with M3 Max Chip",
                    price = "$2499",
                    priceValue = 2499.0,
                    quantity = 1,
                    category = "Laptops"
                ),
                onItemClick = {},
                onDeleteClick = {}
            )

            // Audio item
            CartItemCard(
                cartItem = CartItem(
                    id = "3",
                    name = "AirPods Pro",
                    price = "$249",
                    priceValue = 249.0,
                    quantity = 3,
                    category = "Audio"
                ),
                onItemClick = {},
                onDeleteClick = {}
            )
        }
    }
}

