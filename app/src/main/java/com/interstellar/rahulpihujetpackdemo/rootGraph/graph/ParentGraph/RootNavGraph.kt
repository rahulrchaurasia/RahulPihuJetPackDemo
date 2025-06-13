package com.interstellar.rahulpihujetpackdemo.rootGraph.graph.ParentGraph// 9. ROOT NAVIGATION GRAPH (Updated to use splash)
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.ChildGraph.authNavigation
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.ChildGraph.mainNavigation
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager

import com.interstellar.rahulpihujetpackdemo.ui.screen.SplashScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authManager: AppDataManager
) {
    val isLoggedIn by authManager.isLoggedIn.collectAsState()
    val isFirstTime by authManager.isFirstTime.collectAsState()

    val globalActions = remember { GlobalNavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Dest.Splash,
        popEnterTransition = { NavigationAnimations.slideInLeft },
        popExitTransition = { NavigationAnimations.slideOutRight }
    ) {

        // Splash Screen
        composable<Dest.Splash>(
            enterTransition = { NavigationAnimations.fadeIn },
            exitTransition = { NavigationAnimations.fadeOut }
        ) {
            SplashScreen(
                onSplashComplete = {
                    authManager.checkLoginState()
                    val destination = when {
                        isLoggedIn -> Dest.MainHome
                        else -> Dest.Welcome
                    }
                    globalActions.navigateTo(destination)

                }
            )
        }

        // Auth Graph - Now properly defined
        authNavigation(globalActions, authManager)

        // Main App Graph - Now properly defined
        mainNavigation(globalActions, authManager)
    }
}