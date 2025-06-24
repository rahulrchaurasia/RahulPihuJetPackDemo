package com.interstellar.rahulpihujetpackdemo.presentation.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

// 3. EmptyCartContent.kt - Empty cart state
@Composable
fun EmptyCartContent(
    onContinueShopping: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        // Empty cart illustration
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.ShoppingCartCheckout,
                contentDescription = "Empty Cart",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Add some products to get started",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onContinueShopping,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.ShoppingBag, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start Shopping")
        }
    }
}

// Preview for EmptyCartContent
@Preview(showBackground = true, name = "Empty Cart", showSystemUi = true)
@Composable
fun EmptyCartContentPreview() {
    MaterialTheme {
        EmptyCartContent(
            onContinueShopping = {}
        )
    }
}
