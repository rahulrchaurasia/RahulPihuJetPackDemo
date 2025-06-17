package com.interstellar.rahulpihujetpackdemo.ui

// 8. Main Home Screen with Bottom Navigation
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.LocalNavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager

import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.CustomBottomNavigationBar
import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.model.BottomNavItem
import com.interstellar.rahulpihujetpackdemo.ui.screen.cart.CartDetailScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.home.HomeScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.CartScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.ProfileScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.WishListScreen
import kotlin.system.exitProcess


import android.content.ContextWrapper
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MainHomeScreen(
    globalActions: GlobalNavigationActions,
    authManager: AppDataManager
) {
    val bottomNavController = LocalNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }
    var showExitDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current  // ✅ Get Context in Compose

    // ✅ SMART BackHandler - Only intercept when necessary
    val currentDestination by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = currentDestination?.destination?.route


    // ✅ Auto-sync selectedIndex with current route
    LaunchedEffect(currentRoute) {
        selectedIndex = when (currentRoute) {


            "Dest.Home" -> 0
            "Dest.WishList" -> 1
            "Dest.Cart" -> 2
            "Dest.Profile" -> 3
            else -> selectedIndex
        }
    }

    BackHandler(enabled = true) {
        when (currentRoute) {
            "Dest.Home" -> {
                showExitDialog = true
            }           // Exit confirmation
            "Dest.WishList", "Dest.Cart", "Dest.Profile" -> {
                // ✅ Other tabs - Navigate to Home tab and update UI
                // ✅ Navigate first
                // ✅ Update UI indicator after navigation

                bottomNavController.navigate("Dest.Home") {
                    popUpTo(bottomNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                selectedIndex = 0 // ✅ Update immediately after navigation

            }

            else -> {
                // ✅ Other screens - Normal back navigation
                if (!bottomNavController.popBackStack()) {
                    showExitDialog = true
                }
            }
        }
    }

    if (showExitDialog) {
        ExitConfirmationDialog(
            onConfirm = {
                // ✅ Safe approach
                val activity = context as? Activity
                if (activity != null) {
                    activity.finish()
                } else {
                    // Fallback
                    exitProcess(0)
                }
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }
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
            // ✅ FIXED: Cart Tab - Use CartDetailScreen with correct parameters
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

// ✅ EXIT CONFIRMATION DIALOG
@Composable
fun ExitConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Exit App",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = "Are you sure you want to exit?",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    )
}