package com.interstellar.rahulpihujetpackdemo.presentation.viewmodel

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.util.ReceiptUtils
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val appDataManager: AppDataManager
): ViewModel() {

    // Expose cart data from AppDataManager
    val cartItems = appDataManager.cartItems
    val cartTotal = appDataManager.cartTotal
    val cartCount = appDataManager.cartCount

    var transactionId: String? = null // ✅ store it

//    fun setTransactionId(id: String) {
//        transactionId = id
//        println("🧾 Transaction ID: $id") // ✅ log for debug
//    }


    // Receipt-specific logic
    fun generateReceiptData(): ReceiptData {

        return ReceiptData(
            items = cartItems.value,
            subtotal = cartTotal.value,
            tax = cartTotal.value * 0.08, // 8% tax
            total = cartTotal.value * 1.08,
            itemCount = cartCount.value,
            receiptNumber = ReceiptUtils.generateReceiptNumber(),
            date = ReceiptUtils.getCurrentDateTime(),
            storeName = "Your Store Name",
            storeAddress = "123 Main Street, City, State 12345",
            storePhone = "+1 (555) 123-4567"
        )
    }




    fun clearCart() {
        appDataManager.clearCart()  // or reset cartItems, cartTotal, cartCount

    }


}