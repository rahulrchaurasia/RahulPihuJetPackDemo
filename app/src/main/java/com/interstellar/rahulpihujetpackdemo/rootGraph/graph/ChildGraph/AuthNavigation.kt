package com.interstellar.rahulpihujetpackdemo.rootGraph.graph.ChildGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager
import com.interstellar.rahulpihujetpackdemo.presentation.screen.LoginScreen

import com.interstellar.rahulpihujetpackdemo.presentation.screen.WelcomeScreen

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


fun NavGraphBuilder.authNavigation(
    globalActions: GlobalNavigationActions,
    authManager: AppDataManager
) {

    // Welcome Screen
    composable<Dest.Welcome>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {
        WelcomeScreen(
            onGetStarted = {
                authManager.setNotFirstTime()
                globalActions.navigateToLogin()
            }
        )
    }

    // ✅ ADD THIS - Login Screen (This was missing!)
    composable<Dest.Login>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {
        LoginScreen(
            onLoginSuccess = { email, token ->
                authManager.login(
                    userEmail = email,
                    userToken = token
                )
                globalActions.navigateToHome()
            },
            onNavigateToRegister = {
                globalActions.navigateToRegister()
            }
//            onBackPressed = {
//                globalActions.navigateBack()
//            }
        )
    }

    // ✅ ADD THIS - Register Screen (Also add this if you use navigateToRegister)
//    composable<Dest.Register>(
//        enterTransition = { NavigationAnimations.slideInRight },
//        exitTransition = { NavigationAnimations.slideOutLeft }
//    ) {
//        RegisterScreen(
//            onRegisterSuccess = { email, token ->
//                authManager.login(
//                    userEmail = email,
//                    userToken = token
//                )
//                globalActions.navigateToHome()
//            },
//            onBackToLogin = {
//                globalActions.navigateBack()
//            }
//        )
//    }
}