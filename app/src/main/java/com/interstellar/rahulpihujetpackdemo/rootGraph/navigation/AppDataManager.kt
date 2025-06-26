package com.interstellar.rahulpihujetpackdemo.rootGraph.navigation


// 4. Authentication State Manager


import kotlinx.coroutines.flow.asStateFlow

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.interstellar.rahulpihujetpackdemo.data.local.model.cart.CartItem
import com.interstellar.rahulpihujetpackdemo.data.local.model.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


//val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
@Singleton
class AppDataManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences> // ✅ Hilt injects DataStore
) {

    // Keys for DataStore
    private object PreferencesKeys {
        // Auth Keys
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NAME = stringPreferencesKey("user_name")
        // Cart Keys
        val CART_ITEMS = stringPreferencesKey("cart_items")
        val CART_COUNT = intPreferencesKey("cart_count")
    }

    // Auth State
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isFirstTime = MutableStateFlow(true)
    val isFirstTime: StateFlow<Boolean> = _isFirstTime.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    // Cart State
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _cartCount = MutableStateFlow(0)
    val cartCount: StateFlow<Int> = _cartCount.asStateFlow()

    private val _cartTotal = MutableStateFlow(0.0)
    val cartTotal: StateFlow<Double> = _cartTotal.asStateFlow()


    // Coroutine scope for async operations
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        // Load saved state on initialization
        loadAppState()
    }

    /**
     * Load authentication state from DataStore
     */
    private fun loadAppState() {
        scope.launch {
            try {
                val preferences = dataStore.data.first()

                // Load Auth State
                val savedIsLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
                val savedIsFirstTime = preferences[PreferencesKeys.IS_FIRST_TIME] ?: true

                // Load Cart State
                val cartJson = preferences[PreferencesKeys.CART_ITEMS] ?: "[]"
                val cartItems = Json.decodeFromString<List<CartItem>>(cartJson)

                // Update in-memory state
                _isLoggedIn.value = savedIsLoggedIn
                _isFirstTime.value = savedIsFirstTime
                _cartItems.value = cartItems
                updateCartMetrics()
                _isLoading.value = false

                println("🔍 AppDataManager: Loaded state - Auth: $savedIsLoggedIn, Cart items: ${cartItems.size}")
            } catch (e: Exception) {
                println("❌ AppDataManager: Error loading state - ${e.message}")
                _isLoading.value = false
            }
        }
    }

    /**
     * Login user and save to DataStore
     */
    fun login(userEmail: String = "", userToken: String = "") {
        scope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.IS_LOGGED_IN] = true
                    preferences[PreferencesKeys.IS_FIRST_TIME] = false
                    if (userEmail.isNotBlank()) {
                        preferences[PreferencesKeys.USER_EMAIL] = userEmail
                    }
                    if (userToken.isNotBlank()) {
                        preferences[PreferencesKeys.USER_TOKEN] = userToken
                    }
                }

                _isLoggedIn.value = true
                _isFirstTime.value = false
                println("✅ AppDataManager: User logged in successfully")
            } catch (e: Exception) {
                println("❌ AppDataManager: Login error - ${e.message}")
            }
        }
    }


    /**
     * Logout user and clear DataStore
     */
    fun logout() {
        scope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.IS_LOGGED_IN] = false
                    preferences.remove(PreferencesKeys.USER_TOKEN)
                    preferences.remove(PreferencesKeys.USER_EMAIL)
                    // Clear cart on logout
                    preferences[PreferencesKeys.CART_ITEMS] = "[]"
                    preferences[PreferencesKeys.CART_COUNT] = 0
                }

                _isLoggedIn.value = false
                _cartItems.value = emptyList()
                updateCartMetrics()
                println("✅ AppDataManager: User logged out successfully")
            } catch (e: Exception) {
                println("❌ AppDataManager: Logout error - ${e.message}")
            }
        }
    }

    /**
     * Set user has seen welcome screen
     */
    fun setNotFirstTime() {
        scope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.IS_FIRST_TIME] = false
                }
                _isFirstTime.value = false
                println("✅ AppDataManager: Set not first time")
            } catch (e: Exception) {
                println("❌ AppDataManager: setNotFirstTime error - ${e.message}")
            }
        }
    }

    /**
     * Check if user has valid token (for auto-login)
     */
    suspend fun hasValidToken(): Boolean {
        return try {
            val preferences = dataStore.data.first()
            val token = preferences[PreferencesKeys.USER_TOKEN]
            !token.isNullOrBlank()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get user email
     */
    fun getUserEmail(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_EMAIL] ?: ""
        }
    }

    /**
     * Get user token
     */
    suspend fun getUserToken(): String {
        return try {
            val preferences = dataStore.data.first()
            preferences[PreferencesKeys.USER_TOKEN] ?: ""
        } catch (e: Exception) {
            ""
        }
    }


    fun checkLoginState() {
        loadAppState()
    }



    //////

    private fun updateCartMetrics() {
        val items = _cartItems.value
        _cartCount.value = items.sumOf { it.quantity }
        _cartTotal.value = items.sumOf { it.priceValue * it.quantity }
    }

    private suspend fun saveCartToDataStore() {
        try {
            val cartJson = Json.encodeToString(_cartItems.value)
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.CART_ITEMS] = cartJson
                preferences[PreferencesKeys.CART_COUNT] = _cartCount.value
            }
        } catch (e: Exception) {
            println("❌ AppDataManager: Error saving cart - ${e.message}")
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        scope.launch {
            try {
                val currentItems = _cartItems.value.toMutableList()
                val existingItemIndex = currentItems.indexOfFirst { it.id == product.id }

                // Returns INDEX of first match, or -1 if not found
                if (existingItemIndex != -1) {   // here when we find item {since we use ! operator
                    val existingItem = currentItems[existingItemIndex]


                        // ✅ Replace existing quantity (for ProductDetail updates)
                        currentItems[existingItemIndex] = existingItem.copy(
                            quantity = quantity
                        )
                        println("✅ Cart: Replaced ${product.name} quantity to $quantity")

                } else {
                    // Add new item
                    val cartItem = CartItem(
                        id = product.id,
                        name = product.name,
                        price = product.price,
                        priceValue = product.price.replace("$", "").toDoubleOrNull() ?: 0.0,
                        quantity = quantity,
                        category = product.category
                    )
                    currentItems.add(cartItem)
                }

                _cartItems.value = currentItems
                updateCartMetrics()
                saveCartToDataStore()

                println("✅ Cart: Added ${product.name} (qty: $quantity)")
            } catch (e: Exception) {
                println("❌ Cart: Error adding item - ${e.message}")
            }
        }
    }

    fun removeFromCart(productId: String) {
        scope.launch {
            try {
                val currentItems = _cartItems.value.toMutableList()
                currentItems.removeAll { it.id == productId }

                _cartItems.value = currentItems
                updateCartMetrics()
                saveCartToDataStore()

                println("✅ Cart: Removed product $productId")
            } catch (e: Exception) {
                println("❌ Cart: Error removing item - ${e.message}")
            }
        }
    }

    fun updateCartItemQuantity(productId: String, newQuantity: Int) {
        scope.launch {
            try {
                if (newQuantity <= 0) {
                    removeFromCart(productId)
                    return@launch
                }

                val currentItems = _cartItems.value.toMutableList()
                val itemIndex = currentItems.indexOfFirst { it.id == productId }

                if (itemIndex != -1) {
                    currentItems[itemIndex] = currentItems[itemIndex].copy(quantity = newQuantity)
                    _cartItems.value = currentItems
                    updateCartMetrics()
                    saveCartToDataStore()

                    println("✅ Cart: Updated quantity for $productId to $newQuantity")
                }
            } catch (e: Exception) {
                println("❌ Cart: Error updating quantity - ${e.message}")
            }
        }
    }

    fun clearCart() {
        scope.launch {
            try {
                Log.d("MYAPP", "🧹 Clearing cart now...")
                _cartItems.value = emptyList()
//                val currentItems = _cartItems.value.toMutableList()
//                currentItems.clear()
//                Log.d("MYAPP", "🧹Start Clearing cart now...")
//                _cartItems.value = currentItems

                updateCartMetrics()
                saveCartToDataStore()

                Log.d("MYAPP", "🧹 Cart: Done Cleared all items\"...")

            } catch (e: Exception) {
                println("❌ Cart: Error clearing cart - ${e.message}")
            }
        }
    }

    fun getCartItem(productId: String): CartItem? {
        return _cartItems.value.find { it.id == productId }
    }

    fun isInCart(productId: String): Boolean {
        return _cartItems.value.any { it.id == productId }
    }


}