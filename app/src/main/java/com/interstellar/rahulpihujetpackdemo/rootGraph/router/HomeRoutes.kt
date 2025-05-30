package com.interstellar.rahulpihujetpackdemo.rootGraph.router




// Home Graph Routes
sealed class HomeRoutes(val route: String) {
    data object Main : HomeRoutes("homeFragment")
    data object Products : HomeRoutes("productsFragment")
    data object ProductDetail : HomeRoutes("productDetailFragment/{productId}") {
        fun createRoute(productId: String) = "productDetailFragment/$productId"
    }
    data object CartDetail : HomeRoutes("cartDetailFragment/{productId}/{productName}/{productPrice}") {
        fun createRoute(productId: String, productName: String, productPrice: String) =
            "cartDetailFragment/$productId/$productName/$productPrice"
    }
    data object Cart : HomeRoutes("cartFragment")
    data object Profile : HomeRoutes("profileFragment")
    data object Wishlist : HomeRoutes("wishlistFragment")
}
