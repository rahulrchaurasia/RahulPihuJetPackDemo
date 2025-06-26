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

    //vip :  (navigate + stack clear)
    // Main App Navigation
    // ✅ METHOD 1: navigate() with popUpTo - CREATES NEW INSTANCE
    /*
    // ✅ Using navigateToHome() with popUpTo:

    // ACTION: navController.navigate(Dest.MainHome) { popUpTo(Dest.Splash) { inclusive = true } }
    // RESULT: [MainHome (NEW INSTANCE)]
    //
    // What happens:
    // 1. Creates a NEW MainHome instance
    // 2. Removes everything from Splash onwards (inclusive = true)
    // 3. Fresh MainHome with reset state
    // 4. All previous screens are destroyed
     */
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

    fun navigateToReceipt(transactionId: String) {
        navController.navigate(Dest.Receipt(transactionId))
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


    //vip : (stack-only action)  ******

    // ✅ METHOD 2: popBackStack() - RETURNS TO EXISTING INSTANCE
    // ✅ Using popBackStack(Dest.MainHome):

    // ACTION: navController.popBackStack(Dest.MainHome, inclusive = false)

    // RESULT: [MainHome (EXISTING INSTANCE)]
    //
    // What happens:
    // 1. Returns to the EXISTING MainHome instance
    // 2. Removes screens above MainHome (Products, CartDetail)
    // 3. Preserves MainHome's previous state
    // 4. Only removes screens added after MainHome

    fun popBackToRoute(destination: Dest, inclusive: Boolean = false) {
        navController.popBackStack(destination, inclusive)
    }

    // ✅ NEW: Safe pop back with fallback
    fun safePopBackToRoute(
        destination: Dest,
        inclusive: Boolean = false,
        fallback: (() -> Unit)? = null
    ) {
        val success = navController.popBackStack(destination, inclusive)
        if (!success) {
            fallback?.invoke() ?: navigateBack() // Default fallback is normal back
        }
    }


}