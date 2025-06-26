package com.interstellar.rahulpihujetpackdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.interstellar.rahulpihujetpackdemo.data.local.model.Product
import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val appDataManager: AppDataManager
) : ViewModel() {

    val cartItems = appDataManager.cartItems
    val cartTotal = appDataManager.cartTotal
    val cartCount = appDataManager.cartCount


    fun updateCartItemQuantity(productId: String, newQuantity: Int){

        appDataManager.updateCartItemQuantity(productId, newQuantity)
    }
    fun removeFromCart(productId: String) {
        appDataManager.removeFromCart(productId)
    }
    fun clearCart() {
        appDataManager.clearCart()
    }

    fun getProductById(productId: String): CartItem ? {
        // Pull from local list or repository
        return appDataManager.getCartItem(productId)
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        appDataManager.addToCart(product, quantity)
    }



    fun isInCart(productId: String): Boolean {
        return appDataManager.isInCart(productId)
    }
}
