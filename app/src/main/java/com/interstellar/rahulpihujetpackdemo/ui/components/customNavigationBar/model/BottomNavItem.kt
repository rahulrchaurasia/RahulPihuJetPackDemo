package com.interstellar.rahulpihujetpackdemo.ui.components.customNavigationBar.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest

// Data class for bottom navigation items
data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val destination: Dest
)