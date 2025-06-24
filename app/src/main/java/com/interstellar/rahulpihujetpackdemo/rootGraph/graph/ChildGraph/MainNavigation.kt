package com.interstellar.rahulpihujetpackdemo.rootGraph.graph.ChildGraph

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager

import com.interstellar.rahulpihujetpackdemo.MainScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.carInsurance.CarInsuranceScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.carInsurance.CarJourneyScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.cart.CartDetailScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.product.ProductDetailScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.product.ProductsScreen
import com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.ReceiptScreen

/*
   ***************************************************
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
    ***************************************************
 */

//2️⃣ MAIN APP MODULE
//this for main App Module
//Note: we diveded graph in 3 module

// NavGraph is the actual map that says where each screen lives:
fun NavGraphBuilder.mainNavigation(
    globalActions: GlobalNavigationActions,
    appDataManager: AppDataManager
) {

    // Main Home Screen (with Bottom Navigation)
    composable<Dest.MainHome>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {
        MainScreen(
            globalActions = globalActions,
            authManager = appDataManager
        )
    }

    // Products Screen
    composable<Dest.Products>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {
        ProductsScreen(
            onProductClick = { productId ->
                globalActions.navigateToProductDetail(productId)

            },
            onBackPressed = {
                globalActions.navigateBack()
            }
        )
    }

    // ✅ ProductDetail Screen to Cart Screen
    composable<Dest.ProductDetail>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) { backStackEntry ->
        val productDetail = backStackEntry.toRoute<Dest.ProductDetail>()

        ProductDetailScreen(
            productId = productDetail.productId,

            onBackPressed = {
                globalActions.navigateBack()
            },
            appDataManager = appDataManager,// ✅ Pass appDataManager
            onNavigateToCart = {
                // ✅ Direct navigation to cart
                globalActions.navigateToCart()
            },

        )
    }




    // ✅ ADD THIS - CartDetail Screen (Also add this if you use navigateToCartDetail)
    composable<Dest.CartDetail>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) { backStackEntry ->
        val cartDetail = backStackEntry.toRoute<Dest.CartDetail>()

        CartDetailScreen(

            onGoHome = {
                globalActions.navigateTo(Dest.MainHome)
            },
            onBackToProducts = {
                globalActions.popBackToRoute(Dest.Products)
            },
            onShareProduct = { shareText ->
                // Implement share functionality
                println("Share: $shareText")
            },
            onNavigateToProductDetail = { productId ->
                // ✅ Navigate back to product detail to add more quantity
                globalActions.navigateToProductDetail(productId)
            },
            onNavigateToRecieptScreen = {
                // ✅ Navigate to Receipt Screen
                globalActions.navigateTo(Dest.Receipt)
            },
            appDataManager = appDataManager
        )
    }

    //✅ Receipt Screen
    composable<Dest.Receipt>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {

        ReceiptScreen(
            globalActions = globalActions
//            viewModel = hiltViewModel(),
//            modifier = Modifier
         )
        }


 //✅ ADD THIS - Car Insurance  Screen (Also add this if you use navigateToCartDetail)
    composable<Dest.CarInsurance>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {
        CarInsuranceScreen(
            globalActions = globalActions,
            handleSystemBars = true,
            onNavigateToCarJourney = {
                globalActions.navigateTo(Dest.CarJourney)  //// 🔁 this will use the transition above
            }

        )
    }



    // ✅ CarJourney (outside bottom navigation)
    composable<Dest.CarJourney>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {
        CarJourneyScreen(
            globalActions = globalActions,
            onBackPressed = {
                globalActions.navigateBack()
            }
        )
    }
}