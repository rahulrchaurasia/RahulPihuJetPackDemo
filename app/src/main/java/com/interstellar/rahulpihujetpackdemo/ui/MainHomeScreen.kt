package com.interstellar.rahulpihujetpackdemo.ui

// 8. Main Home Screen with Bottom Navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.LocalNavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager

import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.CustomBottomNavigationBar
import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.model.BottomNavItem
import com.interstellar.rahulpihujetpackdemo.ui.screen.home.HomeScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.CartScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.ProfileScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.WishListScreen


@Composable
fun MainHomeScreen(
    globalActions: GlobalNavigationActions,
    authManager: AppDataManager
) {
    val bottomNavController = LocalNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            destination = Dest.Home
        ),
        BottomNavItem(
            icon = Icons.Default.FavoriteBorder,
            label = "WishList",
            destination = Dest.WishList
        ),
        BottomNavItem(
            icon = Icons.Default.ShoppingCart,
            label = "Cart",
            destination = Dest.Cart
        ),
        BottomNavItem(
            icon = Icons.Default.Person,
            label = "Profile",
            destination = Dest.Profile
        )
    )

    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
                items = navItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                    bottomNavController.navigate(navItems[index].destination) {
                        popUpTo(Dest.Home) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = bottomNavController,
            startDestination = Dest.Home,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA))
        ) {

            // Home Tab
            composable<Dest.Home> {
                HomeScreen(
                    onNavigateToProducts = {
                        globalActions.navigateToProducts()
                    }
                )
            }

            // WishList Tab
            composable<Dest.WishList> {
                WishListScreen()
            }

            // Cart Tab
            composable<Dest.Cart> {
                CartScreen()
            }

            // Profile Tab
            composable<Dest.Profile> {
                ProfileScreen(
                    onLogout = {
                        authManager.logout()
                        globalActions.navigateToWelcome()
                    }
                )
            }
        }
    }
}