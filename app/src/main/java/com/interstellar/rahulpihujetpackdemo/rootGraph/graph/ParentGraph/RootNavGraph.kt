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
/*

Current Navigation Architecture
1. Root Navigation (Global)
kotlinRootNavGraph -> NavHost with globalActions
├── Splash
├── Auth (Welcome, Login, Register)
└── MainHome (Contains bottom navigation)
2. Bottom Navigation (Local)
kotlinMainApp -> Manual tab switching with selectedIndex
├── Home
├── WishList
├── Cart
└── Profile
 */
@Composable
fun RootNavGraph(
    navController: NavHostController,
    appDataManager: AppDataManager
) {
    val isLoggedIn by appDataManager.isLoggedIn.collectAsState()
    val isFirstTime by appDataManager.isFirstTime.collectAsState()

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
                    appDataManager.checkLoginState()
                    val destination = when {
                        isLoggedIn -> Dest.MainHome
                        else -> Dest.Welcome
                    }
                    globalActions.navigateTo(destination)

                }
            )
        }

        // Auth Graph - Now properly defined
        authNavigation(globalActions, appDataManager)

        // Main App Graph - Now properly defined
        mainNavigation(globalActions, appDataManager)
    }
}