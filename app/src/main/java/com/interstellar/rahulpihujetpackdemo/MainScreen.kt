package com.interstellar.rahulpihujetpackdemo

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

import com.interstellar.rahulpihujetpackdemo.presentation.components.customNavigationBar.CustomBottomNavigationBar
import com.interstellar.rahulpihujetpackdemo.presentation.components.customNavigationBar.model.BottomNavItem
import com.interstellar.rahulpihujetpackdemo.presentation.screen.home.HomeScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.module.CartScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.module.ProfileScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.module.WishListScreen
import kotlin.system.exitProcess


import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.interstellar.rahulpihujetpackdemo.presentation.screen.carInsurance.CarInsuranceScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.transaction.TransactionScreen
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations


@Composable
fun MainScreen(
    globalActions: GlobalNavigationActions,
   // authManager: AppDataManager
) {
    val bottomNavController = LocalNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }
    var showExitDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current  // ✅ Get Context in Compose

    // ✅ SMART BackHandler - Only intercept when necessary
    val currentDestination by bottomNavController.currentBackStackEntryAsState()
    //val currentRoute = currentDestination?.destination?.route


    // ✅ Auto-sync selectedIndex with current route
    //// ✅ ONLY this LaunchedEffect controls selectedIndex
    LaunchedEffect(currentDestination) {
        currentDestination?.let { entry ->
            val route = entry.destination.route
            selectedIndex = when {
                route?.contains("Dest.Home") == true -> 0
                route?.contains("Dest.WishList") == true -> 1
                route?.contains("Dest.Cart") == true -> 2
                route?.contains("Dest.Profile") == true -> 3
                else -> selectedIndex // Keep current if unknown route
            }
        }
    }


    // ✅ IMPROVED: Smart back handling with proper tab indicator sync
    BackHandler(enabled = true) {
        when {
            selectedIndex == 0 -> {
                // On Home tab - show exit dialog
                showExitDialog = true
            }
            else -> {
                // On other tabs - navigate to Home and update indicator
                navigateToHome(bottomNavController)
                // selectedIndex will be updated automatically by LaunchedEffect
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
                    // ✅ FIXED: Only navigate if different tab selected
                    if (index != selectedIndex) {
                        navigateToTab(bottomNavController, index, navItems)
                        // selectedIndex will be updated automatically by LaunchedEffect
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
                    },
                    onNavigateToCart = {
                        globalActions.navigateToCart()
                    }
                )
            }

            // WishList Tab
        //    composable<Dest.WishList> {
//                WishListScreen()
//            }
            composable<Dest.WishList> {
                CarInsuranceScreen(
                    globalActions = globalActions,
                    handleSystemBars = false,
                    onNavigateToCarJourney = {
                        globalActions.navigateTo(Dest.CarJourney)  // Goes to mainNavigation
                    }
                )
            }

            // Cart Tab
            // ✅ FIXED: Cart Tab - Use CartDetailScreen with correct parameters
            composable<Dest.Cart> {
                //CartScreen()

                TransactionScreen(
                   // navController = bottomNavController, // ✅ Added required navController
                    globalActions = globalActions,
                    modifier = Modifier.fillMaxSize() ,   // ✅ Fixed typo: "modifire" -> "modifier"
                    onNavigateToCarInsurance = {

                        globalActions.navigateTo(Dest.CarInsurance)
                    }
                )
            }



            // Profile Tab
            composable<Dest.Profile> {
                ProfileScreen(
                    onLogout = {
                       // authManager.logout()
                        globalActions.navigateToLogin()
                    }
                )
            }
        }
    }
}

// ✅ Helper function for navigating to home
private fun navigateToHome(navController: NavHostController) {
    navController.navigate(Dest.Home) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

// ✅ Helper function for tab navigation
private fun navigateToTab(
    navController: NavHostController,
    index: Int,
    navItems: List<BottomNavItem>
) {
    val destination = navItems[index].destination

    navController.navigate(destination) {
        // Pop up to the start destination to avoid building up a large stack
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
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