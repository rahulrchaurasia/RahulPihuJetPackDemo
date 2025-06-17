package com.interstellar.rahulpihujetpackdemo.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: String,
    val name: String,
    val price: String,
    val priceValue: Double, // For calculations
    val quantity: Int = 1,
    val category: String = "",
    val imageUrl: String = "",
    val addedAt: Long = System.currentTimeMillis()
)
