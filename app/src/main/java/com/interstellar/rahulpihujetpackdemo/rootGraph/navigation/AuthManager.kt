package com.interstellar.rahulpihujetpackdemo.rootGraph.navigation


import androidx.datastore.preferences.preferencesDataStore

// 4. Authentication State Manager


import kotlinx.coroutines.flow.asStateFlow

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class AuthManager {
//    private val _isLoggedIn = MutableStateFlow(false)
//    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
//
//    private val _isFirstTime = MutableStateFlow(true)
//    val isFirstTime: StateFlow<Boolean> = _isFirstTime.asStateFlow()
//
//    fun login() {
//        _isLoggedIn.value = true
//        _isFirstTime.value = false
//    }
//
//    fun logout() {
//        _isLoggedIn.value = false
//    }
//
//    fun setNotFirstTime() {
//        _isFirstTime.value = false
//    }
//
//    fun checkLoginState() {
//        // Simulate checking SharedPreferences/DataStore
//        // In real app: check stored auth token
//        _isLoggedIn.value = false
//        _isFirstTime.value = true
//    }
//}

// DataStore extension
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

class AuthManager(private val context: Context) {

    // Keys for DataStore
    private object PreferencesKeys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NAME = stringPreferencesKey("user_name")
    }

    // In-memory state for UI reactivity
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isFirstTime = MutableStateFlow(true)
    val isFirstTime: StateFlow<Boolean> = _isFirstTime.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Coroutine scope for async operations
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        // Load saved state on initialization
        loadAuthState()
    }

    /**
     * Load authentication state from DataStore
     */
    private fun loadAuthState() {
        scope.launch {
            try {
                val preferences = context.dataStore.data.first()

                val savedIsLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
                val savedIsFirstTime = preferences[PreferencesKeys.IS_FIRST_TIME] ?: true

                // Update in-memory state
                _isLoggedIn.value = savedIsLoggedIn
                _isFirstTime.value = savedIsFirstTime
                _isLoading.value = false

                println("🔍 AuthManager: Loaded state - isLoggedIn: $savedIsLoggedIn, isFirstTime: $savedIsFirstTime")
            } catch (e: Exception) {
                println("❌ AuthManager: Error loading state - ${e.message}")
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
                context.dataStore.edit { preferences ->
                    preferences[PreferencesKeys.IS_LOGGED_IN] = true
                    preferences[PreferencesKeys.IS_FIRST_TIME] = false
                    if (userEmail.isNotBlank()) {
                        preferences[PreferencesKeys.USER_EMAIL] = userEmail
                    }
                    if (userToken.isNotBlank()) {
                        preferences[PreferencesKeys.USER_TOKEN] = userToken
                    }
                }

                // Update in-memory state
                _isLoggedIn.value = true
                _isFirstTime.value = false

                println("✅ AuthManager: User logged in successfully")
            } catch (e: Exception) {
                println("❌ AuthManager: Login error - ${e.message}")
            }
        }
    }

    /**
     * Logout user and clear DataStore
     */
    fun logout() {
        scope.launch {
            try {
                context.dataStore.edit { preferences ->
                    preferences[PreferencesKeys.IS_LOGGED_IN] = false
                    // Keep isFirstTime as false (user has seen welcome screen)
                    // Clear sensitive data
                    preferences.remove(PreferencesKeys.USER_TOKEN)
                    preferences.remove(PreferencesKeys.USER_EMAIL)
                }

                // Update in-memory state
                _isLoggedIn.value = false

                println("✅ AuthManager: User logged out successfully")
            } catch (e: Exception) {
                println("❌ AuthManager: Logout error - ${e.message}")
            }
        }
    }

    /**
     * Set user has seen welcome screen
     */
    fun setNotFirstTime() {
        scope.launch {
            try {
                context.dataStore.edit { preferences ->
                    preferences[PreferencesKeys.IS_FIRST_TIME] = false
                }

                _isFirstTime.value = false

                println("✅ AuthManager: Set not first time")
            } catch (e: Exception) {
                println("❌ AuthManager: setNotFirstTime error - ${e.message}")
            }
        }
    }

    /**
     * Check if user has valid token (for auto-login)
     */
    suspend fun hasValidToken(): Boolean {
        return try {
            val preferences = context.dataStore.data.first()
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
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_EMAIL] ?: ""
        }
    }

    /**
     * Get user token
     */
    suspend fun getUserToken(): String {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[PreferencesKeys.USER_TOKEN] ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * Clear all data (for app reset)
     */
    fun clearAllData() {
        scope.launch {
            try {
                context.dataStore.edit { preferences ->
                    preferences.clear()
                }

                _isLoggedIn.value = false
                _isFirstTime.value = true

                println("✅ AuthManager: All data cleared")
            } catch (e: Exception) {
                println("❌ AuthManager: Clear data error - ${e.message}")
            }
        }
    }

    /**
     * Legacy method for compatibility (deprecated)
     */
    @Deprecated("Use loadAuthState() instead")
    fun checkLoginState() {
        loadAuthState()
    }
}