package com.interstellar.rahulpihujetpackdemo.ui

// 8. Main Home Screen with Bottom Navigation
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AuthManager
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.CustomBottomNavigationBar
import com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.model.BottomNavItem
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.CartScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.HomeTabContent
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.ProfileScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.module.WishListScreen


@Composable
fun MainHomeScreen(
    localNavController: NavController,
    globalActions: GlobalNavigationActions,
    authManager: AuthManager
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem(Icons.Default.Home, "Home"),
        BottomNavItem(Icons.Default.FavoriteBorder, "Wishlist" ),
        BottomNavItem(Icons.Default.ShoppingCart, "Cart" ),
        BottomNavItem(Icons.Default.Person, "Profile")
    )

    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
                items = navItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                },
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedIndex) {
                0 -> HomeTabContent(localNavController = localNavController)
                1 -> WishListScreen()
                2 -> CartScreen()
                3 -> ProfileScreen(
                    onLogout = {
                        authManager.logout()
                        globalActions.actionGlobalToAuth()
                    }
                )
            }
        }
    }
}
