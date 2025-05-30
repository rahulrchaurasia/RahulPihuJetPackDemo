package com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.model

import androidx.compose.ui.graphics.vector.ImageVector

// Data class for bottom navigation items
data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: String? = null
)
