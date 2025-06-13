package com.interstellar.rahulpihujetpackdemo.ui.screen



import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.delay


//@Composable
//fun SplashScreen(onSplashComplete: () -> Unit) {
//    LaunchedEffect(Unit) {
//        delay(2000)
//        onSplashComplete()
//    }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                text = "ShopApp",
//                style = MaterialTheme.typography.headlineLarge
//            )
//            CircularProgressIndicator()
//        }
//    }
//}

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "alpha_animation"
    )

    val scaleAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = tween(
            durationMillis = 1500,
            easing = FastOutSlowInEasing
        ),
        label = "scale_animation"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2500) // Show splash for 2.5 seconds
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .alpha(alphaAnimation)
                .scale(scaleAnimation)
        ) {
            // App Logo
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ShopApp",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Shopping Companion",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f)
            )
        }

        // Loading indicator at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
               .windowInsetsPadding(WindowInsets.systemBars) // ✅ Add this for full safe area
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}