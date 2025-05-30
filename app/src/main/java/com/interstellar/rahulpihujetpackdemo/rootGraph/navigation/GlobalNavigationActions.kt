package com.interstellar.rahulpihujetpackdemo.rootGraph.navigation// 2. Global Navigation Actions Manager
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.NavGraphs
import com.interstellar.rahulpihujetpackdemo.rootGraph.router.AuthRoutes

class GlobalNavigationActions(private val navController: NavController) {
    
    // Global action to home_graph (after login success)
    fun actionGlobalToHome() {
        navController.navigate(NavGraphs.HOME) {
            popUpTo(NavGraphs.ROOT) { inclusive = true }
            // Equivalent to XML enterAnim/exitAnim
            launchSingleTop = true
        }
    }
    
    // Global action to login (direct to login, not welcome)
    fun actionGlobalToLogin() {
        navController.navigate(AuthRoutes.Login.route) {
            popUpTo(NavGraphs.ROOT) { inclusive = true }
            launchSingleTop = true
        }
    }
    
    // Global action to auth_graph (logout - goes to welcome as start destination)
    fun actionGlobalToAuth() {
        navController.navigate(NavGraphs.AUTH) {
            popUpTo(NavGraphs.ROOT) { inclusive = true }
            launchSingleTop = true
        }
    }
}