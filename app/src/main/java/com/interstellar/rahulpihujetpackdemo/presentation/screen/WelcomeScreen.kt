package com.interstellar.rahulpihujetpackdemo.presentation.screen


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp


// 8. Auth Screens (Your existing ones work perfectly)
@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    val welcomePages = listOf(
        WelcomePage(
            icon = Icons.Default.ShoppingCart,
            title = "Shop with Ease",
            description = "Browse thousands of products from your favorite brands"
        ),
        WelcomePage(
            icon = Icons.Default.Favorite,
            title = "Save Your Favorites",
            description = "Create wishlists and never lose track of items you love"
        ),
        WelcomePage(
            icon = Icons.Default.LocalShipping,
            title = "Fast Delivery",
            description = "Get your orders delivered quickly and safely to your door"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars) // ✅ Add this for full safe area
        // OR use this for just navigation bar:
        // .windowInsetsPadding(WindowInsets.navigationBars)
    ) {

        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            WelcomePageContent(welcomePages[page])
        }

        // Page Indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(welcomePages.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(
                            width = if (isSelected) 24.dp else 8.dp,
                            height = 8.dp
                        )
                        .background(
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .animateContentSize()
                )
            }
        }

        // Get Started Button
        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Get Started",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class WelcomePage(
    val icon: ImageVector,
    val title: String,
    val description: String
)

@Composable
private fun WelcomePageContent(page: WelcomePage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = page.icon,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 24.sp
        )
    }
}