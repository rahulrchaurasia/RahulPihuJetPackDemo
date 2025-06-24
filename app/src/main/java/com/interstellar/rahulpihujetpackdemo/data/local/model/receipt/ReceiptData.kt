package com.interstellar.rahulpihujetpackdemo.data.local.model.receipt

import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem

data class ReceiptData(
    val items: List<CartItem>,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val itemCount: Int,
    val receiptNumber: String,
    val date: String,
    val storeName: String,
    val storeAddress: String,
    val storePhone: String
)