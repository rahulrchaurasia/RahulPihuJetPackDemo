package com.interstellar.rahulpihujetpackdemo.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.basicUI.Demo1AcceessStringResource
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AuthManager
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.router.HomeRoutes
import com.interstellar.rahulpihujetpackdemo.ui.theme.RahulPihuJetPackDemoTheme


// 3. NEW CartDetailScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailScreen(
    productId: String,
    productName: String,
    productPrice: String,
    onGoHome: () -> Unit,
    onBackToProducts: () -> Unit,
    onShareProduct: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Added to Cart") },
            actions = {
                IconButton(
                    onClick = {
                        val shareText = "Check out this $productName for $productPrice!"
                        onShareProduct(shareText)
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }
        )

        // Cart Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Success Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "Added to Cart!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Product Info Card
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = productName,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = productPrice,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Product Image Placeholder
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("IMG", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Divider()

                    Text(
                        text = "Quantity: 1",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Total: $productPrice",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onGoHome,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Continue Shopping")
                }

                OutlinedButton(
                    onClick = onBackToProducts,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Back to Products")
                }
            }
        }
    }
}
