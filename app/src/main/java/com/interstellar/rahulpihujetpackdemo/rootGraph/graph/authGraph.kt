package com.interstellar.rahulpihujetpackdemo.rootGraph.graph// 6. AUTH GRAPH (Equivalent to auth_graph.xml)
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.interstellar.rahulpihujetpackdemo.rootGraph.NavGraphs
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AuthManager
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.LocalNavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.router.AuthRoutes
import com.interstellar.rahulpihujetpackdemo.ui.screen.LoginScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.RegisterScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.WelcomeScreen

fun NavGraphBuilder.authGraph(
    globalActions: GlobalNavigationActions,
    authManager: AuthManager
) {
    navigation(
        startDestination = AuthRoutes.Welcome.route, // Start destination is Welcome
        route = NavGraphs.AUTH
    ) {
        
        // Welcome Fragment
        composable(
            route = AuthRoutes.Welcome.route,
            enterTransition = { NavigationAnimations.slideInRight },
            exitTransition = { NavigationAnimations.slideOutLeft }
        ) {

            val localNavController = LocalNavController.current
            WelcomeScreen(
                onGetStarted = {
                    println("Welcome: Get Started clicked!") // Debug log
                    authManager.setNotFirstTime()
                    // Navigate to login within auth graph (LOCAL navigation)
                    localNavController.navigate(AuthRoutes.Login.route)
                    println("Welcome: Navigated to ${AuthRoutes.Login.route}") // Debug log

                }
            )
        }
        
        // Login Fragment  
        composable(
            route = AuthRoutes.Login.route,
            enterTransition = { NavigationAnimations.slideInRight },
            exitTransition = { NavigationAnimations.slideOutLeft }
        ) {
            val localNavController = LocalNavController.current
            LoginScreen(
                onLoginSuccess = { email, token ->

                    // Pass user data to AuthManager for persistence
                    authManager.login(
                        userEmail = email,
                        userToken = token
                    )
                    globalActions.actionGlobalToHome() // Use global action
                },
                onNavigateToRegister = {
                    localNavController.navigate(AuthRoutes.Register.route)
                }
            )
        }
        
        // Register Fragment
        composable(
            route = AuthRoutes.Register.route,
            enterTransition = { NavigationAnimations.slideInRight },
            exitTransition = { NavigationAnimations.slideOutLeft }
        ) {
            val localNavController = LocalNavController.current
            RegisterScreen(
                onRegisterSuccess = {
                    authManager.login()
                    globalActions.actionGlobalToHome() // Use global action
                },
                onBackToLogin = {
                    localNavController.popBackStack()
                }
            )
        }
    }
}