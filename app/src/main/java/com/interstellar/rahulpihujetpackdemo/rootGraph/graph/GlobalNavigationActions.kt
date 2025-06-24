package com.interstellar.rahulpihujetpackdemo.rootGraph.graph

import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations

//Note: designed to abstract and centralize navigation logic
class GlobalNavigationActions(private val navController: NavController) {
    
    // Auth Navigation
    fun navigateToWelcome() {
        navController.navigate(Dest.Welcome) {
            popUpTo(0) { inclusive = true }
        }
    }
    
    fun navigateToLogin() {
        navController.navigate(Dest.Login)
    }
    
    fun navigateToRegister() {
        navController.navigate(Dest.Register)
    }
    
    // Main App Navigation
    fun navigateToHome() {
        navController.navigate(Dest.MainHome) {
            popUpTo(Dest.Splash) { inclusive = true }
        }
    }
    
    // Feature Navigation
    fun navigateToProducts() {
        navController.navigate(Dest.Products)
    }
    
    fun navigateToProductDetail(productId: String) {
        navController.navigate(Dest.ProductDetail(productId))
    }



    fun navigateToCart() {
        // ✅ NEW: Direct navigation to cart
        navController.navigate(Dest.CartDetail("", "", ""))
    }


    // Generic Navigation
    // ✅ PERFECT: Generic navigation method handles everything
    fun navigateTo(destination: Dest) {
        navController.navigate(destination)
    }
    
    fun navigateBack() {
        navController.popBackStack()
    }
    
    fun popBackToRoute(destination: Dest, inclusive: Boolean = false) {
        navController.popBackStack(destination, inclusive)
    }



}