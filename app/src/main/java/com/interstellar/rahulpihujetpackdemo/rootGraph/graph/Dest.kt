package com.interstellar.rahulpihujetpackdemo.rootGraph.graph

import kotlinx.serialization.Serializable

@Serializable
sealed class Dest {

    @Serializable
    data object Splash : Dest()

    // Auth Destinations
    @Serializable
    data object Welcome : Dest()

    @Serializable
    data object Login : Dest()

    @Serializable
    data object Register : Dest()

    // Main App Destinations
    @Serializable
    data object MainHome : Dest()

    // Bottom Navigation Destinations
    @Serializable
    data object Home : Dest()

    @Serializable
    data object WishList : Dest()

    @Serializable
    data object Cart : Dest()

    @Serializable
    data object Profile : Dest()

    // Feature Destinations
    @Serializable
    data object Products : Dest()

    @Serializable
    data class ProductDetail(
        val productId: String
    ) : Dest()

    @Serializable
    data class CartDetail(
        val productId: String,
        val productName: String,
        val productPrice: String
    ) : Dest()
}