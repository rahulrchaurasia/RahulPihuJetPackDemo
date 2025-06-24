package com.interstellar.rahulpihujetpackdemo.presentation.components.loading

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex

// Enhanced Loading Configuration
data class LoadingConfig(
    val message: String = "Loading...",
    val showBackground: Boolean = true,
    val backgroundColor: Color = Color.Black.copy(alpha = 0.5f),
    val canDismiss: Boolean = false,
    val loadingType: LoadingType = LoadingType.CIRCULAR,
    val size: androidx.compose.ui.unit.Dp = 48.dp,
    val strokeWidth: androidx.compose.ui.unit.Dp = 4.dp
)

enum class LoadingType {
    CIRCULAR,
    LINEAR,
    DOTS,
    PULSE,
    SHIMMER
}

// 1. Universal Loading Overlay (Most Versatile)
@Composable
fun UniversalLoading(
    isLoading: Boolean,
    config: LoadingConfig = LoadingConfig(),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.zIndex(999f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (config.showBackground) config.backgroundColor else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                LoadingContent(config = config)
            }
        }
    }
}

// 2. Loading Dialog (For Critical Operations)
@Composable
fun LoadingDialog(
    isLoading: Boolean,
    config: LoadingConfig = LoadingConfig(message = "Please wait...")
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = { if (config.canDismiss) { /* Handle dismiss */ } },
            properties = DialogProperties(
                dismissOnBackPress = config.canDismiss,
                dismissOnClickOutside = config.canDismiss
            )
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                LoadingContent(
                    config = config,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

// 3. Inline Loading (For Buttons/Small Areas)
@Composable
fun InlineLoading(
    modifier: Modifier = Modifier,
    message: String = "",
    size: androidx.compose.ui.unit.Dp = 24.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            strokeWidth = 2.dp,
            color = color
        )
        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// 4. Loading Content Component
@Composable
private fun LoadingContent(
    config: LoadingConfig,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (config.loadingType) {
            LoadingType.CIRCULAR -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(config.size),
                    strokeWidth = config.strokeWidth,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            LoadingType.LINEAR -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .width(config.size * 2)
                            .height(config.strokeWidth)
                            .clip(RoundedCornerShape(config.strokeWidth / 2)),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            LoadingType.DOTS -> {
                DotsLoading(size = config.size)
            }
            LoadingType.PULSE -> {
                PulseLoading(size = config.size)
            }
            LoadingType.SHIMMER -> {
                ShimmerLoading(size = config.size)
            }
        }

        if (config.message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = config.message,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = if (config.showBackground) Color.White else MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

// 5. Custom Loading Animations
@Composable
private fun DotsLoading(size: androidx.compose.ui.unit.Dp) {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 200)
                ),
                label = "scale"
            )

            Box(
                modifier = Modifier
                    .size(size / 4)
                    .scale(scale)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
            )
        }
    }
}

@Composable
private fun PulseLoading(size: androidx.compose.ui.unit.Dp) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .size(size)
            .scale(scale)
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                CircleShape
            )
    )
}

@Composable
private fun ShimmerLoading(size: androidx.compose.ui.unit.Dp) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = alpha),
                RoundedCornerShape(8.dp)
            )
    )
}