package com.interstellar.rahulpihujetpackdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.util.ReceiptUtils
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PaymentViewModel @Inject constructor(
    private val appDataManager: AppDataManager
) : ViewModel() {

    // Expose cart data from AppDataManager
    val cartItems = appDataManager.cartItems
    val cartTotal = appDataManager.cartTotal
    val cartCount = appDataManager.cartCount

    // Payment form states
    private val _selectedPaymentMethod = MutableStateFlow("Credit Card")
    val selectedPaymentMethod = _selectedPaymentMethod.asStateFlow()

    private val _cardNumber = MutableStateFlow("")
    val cardNumber = _cardNumber.asStateFlow()

    private val _expiryDate = MutableStateFlow("")
    val expiryDate = _expiryDate.asStateFlow()

    private val _cvv = MutableStateFlow("")
    val cvv = _cvv.asStateFlow()

    private val _cardHolderName = MutableStateFlow("")
    val cardHolderName = _cardHolderName.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val _paymentSuccess = MutableSharedFlow<String>()
    val paymentSuccess = _paymentSuccess.asSharedFlow()

    private val _paymentError = MutableSharedFlow<String>()
    val paymentError = _paymentError.asSharedFlow()

    fun updateSelectedPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
    }

    fun updateCardNumber(number: String) {
        _cardNumber.value = number
    }

    fun updateExpiryDate(date: String) {
        _expiryDate.value = date
    }

    fun updateCvv(cvv: String) {
        _cvv.value = cvv
    }

    fun updateCardHolderName(name: String) {
        _cardHolderName.value = name
    }

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

    fun processPayment() {
        viewModelScope.launch {
            try {
                _isProcessing.value = true
                
                // Validate payment details if card payment
                if (_selectedPaymentMethod.value in listOf("Credit Card", "Debit Card")) {
                    if (!validateCardDetails()) {
                        _paymentError.emit("Please fill in all card details")
                        return@launch
                    }
                }

                // Simulate payment processing
                delay(2000)
                
                // For demo purposes - simulate success
                val transactionId = "TXN_${System.currentTimeMillis()}"
                

                // Reset payment form
                resetPaymentForm()
                
                _paymentSuccess.emit(transactionId)
                
            } catch (e: Exception) {
                _paymentError.emit(e.message ?: "Payment failed")
            } finally {
                _isProcessing.value = false
            }
        }
    }

    private fun validateCardDetails(): Boolean {
        return _cardNumber.value.isNotBlank() &&
                _expiryDate.value.isNotBlank() &&
                _cvv.value.isNotBlank() &&
                _cardHolderName.value.isNotBlank()
    }

    fun resetPaymentForm() {
        _selectedPaymentMethod.value = "Credit Card"
        _cardNumber.value = ""
        _expiryDate.value = ""
        _cvv.value = ""
        _cardHolderName.value = ""
    }
}