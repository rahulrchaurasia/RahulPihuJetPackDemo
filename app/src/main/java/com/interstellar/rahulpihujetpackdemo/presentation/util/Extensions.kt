package com.interstellar.rahulpihujetpackdemo.presentation.util

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

// 🔹 Shimmer background effect
fun Modifier.shimmerEffect(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer-alpha"
    )
    this.background(Color.Gray.copy(alpha = alpha))
}

@Composable
fun <T> rememberMutableStateOf(initial: T) = remember { mutableStateOf(initial) }

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.lowercase().replaceFirstChar(Char::uppercaseChar) }

fun Modifier.horizontalPadding(): Modifier = padding(horizontal = 16.dp)

@Composable
fun SpacerHeight(height: Dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun SpacerWidth(width: Dp) = Spacer(modifier = Modifier.width(width))


// 2. Use it in Any Composable

@Composable
@Preview(showBackground = true, name = "ShimmerBox - Default")

fun ShimmerBoxPreview() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .shimmerEffect()
    )
}