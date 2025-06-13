package com.interstellar.rahulpihujetpackdemo.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val price: String,
    val category: String,
    val imageUrl: String = "",
    val description: String = "",
    val rating: Float = 0f,
    val reviewCount: Int = 0
)