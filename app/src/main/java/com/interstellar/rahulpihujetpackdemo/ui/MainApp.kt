package com.interstellar.rahulpihujetpackdemo.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.CustomBottomNavigationBar
import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.model.BottomNavItem
import com.interstellar.rahulpihujetpackdemo.ui.screen.home.CartScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.home.HomeScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.home.ProfileScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.home.WishListScreen

@Composable
fun MainApp() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home"
        ),
        BottomNavItem(
            icon = Icons.Default.FavoriteBorder,
            label = "WishList"
        ),
        BottomNavItem(
            icon = Icons.Default.ShoppingCart,
            label = "Cart"
        ),
        BottomNavItem(
            icon = Icons.Default.Person,
            label = "Profile"
        )
    )

    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
                items = navItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                },
                        // Add navigation bar padding to bottom bar
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA)),
            contentAlignment = Alignment.Center
        ) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> WishListScreen()
                2 -> CartScreen()
                3 -> ProfileScreen()
            }
        }
    }
}