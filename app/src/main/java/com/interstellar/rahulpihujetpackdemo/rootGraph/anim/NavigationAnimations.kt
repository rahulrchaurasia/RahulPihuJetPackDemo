package com.interstellar.rahulpihujetpackdemo.rootGraph.anim


// 1. ENHANCED NavigationAnimations with Bidirectional Support
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

// 3. Animation Configurations (XML slide animations)
object NavigationAnimations {
    private const val ANIMATION_DURATION = 300

    // Forward Navigation (Left to Right)
    val slideInRight = slideInHorizontally(
        initialOffsetX = { it }, // Start from right edge
        animationSpec = tween(ANIMATION_DURATION)
    )

    val slideOutLeft = slideOutHorizontally(
        targetOffsetX = { -it }, // Exit to left edge
        animationSpec = tween(ANIMATION_DURATION)
    )

    // Backward Navigation (Right to Left) - NEW
    val slideInLeft = slideInHorizontally(
        initialOffsetX = { -it }, // Start from left edge
        animationSpec = tween(ANIMATION_DURATION)
    )

    val slideOutRight = slideOutHorizontally(
        targetOffsetX = { it }, // Exit to right edge
        animationSpec = tween(ANIMATION_DURATION)
    )

    // Fade animations for special cases
    val fadeIn = fadeIn(animationSpec = tween(ANIMATION_DURATION))
    val fadeOut = fadeOut(animationSpec = tween(ANIMATION_DURATION))
}