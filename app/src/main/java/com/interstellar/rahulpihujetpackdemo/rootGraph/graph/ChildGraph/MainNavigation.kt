package com.interstellar.rahulpihujetpackdemo.rootGraph.graph.ChildGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.interstellar.rahulpihujetpackdemo.rootGraph.anim.NavigationAnimations
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager

import com.interstellar.rahulpihujetpackdemo.ui.MainHomeScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.cart.CartDetailScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.product.ProductDetailScreen
import com.interstellar.rahulpihujetpackdemo.ui.screen.product.ProductsScreen

fun NavGraphBuilder.mainNavigation(
    globalActions: GlobalNavigationActions,
    authManager: AppDataManager
) {

    // Main Home Screen (with Bottom Navigation)
    composable<Dest.MainHome>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) {
        MainHomeScreen(
            globalActions = globalActions,
            authManager = authManager
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

    // ✅ ADD THIS - ProductDetail Screen (This was missing!)
    composable<Dest.ProductDetail>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) { backStackEntry ->
        val productDetail = backStackEntry.toRoute<Dest.ProductDetail>()

        ProductDetailScreen(
            productId = productDetail.productId,
            onAddToCart = { id, name, price ->
                globalActions.navigateToCartDetail(id, name, price)
            },
            onBackPressed = {
                globalActions.navigateBack()
            }
        )
    }

    // ✅ ADD THIS - CartDetail Screen (Also add this if you use navigateToCartDetail)
    composable<Dest.CartDetail>(
        enterTransition = { NavigationAnimations.slideInRight },
        exitTransition = { NavigationAnimations.slideOutLeft }
    ) { backStackEntry ->
        val cartDetail = backStackEntry.toRoute<Dest.CartDetail>()

        CartDetailScreen(
            productId = cartDetail.productId,
            productName = cartDetail.productName,
            productPrice = cartDetail.productPrice,
            onGoHome = {
                globalActions.navigateTo(Dest.MainHome)
            },
            onBackToProducts = {
                globalActions.popBackToRoute(Dest.Products)
            },
            onShareProduct = { shareText ->
                println("Share: $shareText")
            }
        )
    }
}