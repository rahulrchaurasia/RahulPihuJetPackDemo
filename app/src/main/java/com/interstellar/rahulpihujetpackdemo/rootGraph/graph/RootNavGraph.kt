package com.interstellar.rahulpihujetpackdemo.rootGraph.graph// 9. ROOT NAVIGATION GRAPH (Updated to use splash)
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.interstellar.rahulpihujetpackdemo.rootGraph.NavGraphs
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AuthManager

import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.ui.screen.SplashScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authManager: AuthManager
) {
    val isLoggedIn by authManager.isLoggedIn.collectAsState()
    val isFirstTime by authManager.isFirstTime.collectAsState()
    
    val globalActions = remember { GlobalNavigationActions(navController) }
    
    NavHost(
        navController = navController,
        startDestination = "splash",
        route = NavGraphs.ROOT,

                // ✅ ADD ONLY THESE 2 LINES - DON'T CHANGE ANYTHING ELSE
        popEnterTransition = { NavigationAnimations.slideInLeft },   // ← Back enter
        popExitTransition = { NavigationAnimations.slideOutRight }   // ← Back exit
    ) {
        
        // Splash Screen
        composable("splash") {
            SplashScreen(
                onSplashComplete = {
                    authManager.checkLoginState()
                    val destination = when {
                        isLoggedIn -> NavGraphs.HOME
                        else -> NavGraphs.AUTH
                    }
                    navController.navigate(destination) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        
        // Your existing authGraph and homeGraph
        authGraph(
            globalActions = globalActions,
            authManager = authManager
        )
        
        homeGraph(
            globalActions = globalActions,
            authManager = authManager
        )
    }
}