package com.interstellar.rahulpihujetpackdemo.presentation.screen.transaction


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
 fun ProgressiveCollapsingHeader(
    height: Dp,
    scrollProgress: Float,
    titleAlpha: Float,
    searchContentAlpha: Float,
    backgroundProgress: Float,
    searchIconAlpha: Float,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .shadow(elevation = (8 * scrollProgress).dp)
    ) {
        // ✅ PROGRESSIVE BACKGROUND - Gradual transition from teal to white
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF26A69A).copy(alpha = 1f - backgroundProgress)
                )
        )

        // White overlay for collapsed state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = backgroundProgress))
        )

        // ✅ TOOLBAR - Always visible
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    // ✅ PROGRESSIVE tint change
                    tint = Color.White.copy(alpha = 1f - backgroundProgress)
                        .compositeOver(Color.Black.copy(alpha = backgroundProgress))
                )
            }

            // ✅ PROGRESSIVE title appearance (only at 90% collapse)
            Text(
                text = "Transactions",
                modifier = Modifier
                    .weight(1f)
                    .alpha(titleAlpha),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            // ✅ PROGRESSIVE search icon (only when fully collapsed)
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier.alpha(searchIconAlpha)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Black
                )
            }
        }

        // ✅ PROGRESSIVE expanded content - Better sync with header collapse
        if (searchContentAlpha > 0.01f) { // Slightly higher threshold for better sync //0.05f
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .alpha(searchContentAlpha)
                    .graphicsLayer {
                        // ✅ REFINED scaling and translation for better sync
                        scaleX = 1f - (scrollProgress * 0.08f)
                        scaleY = 1f - (scrollProgress * 0.08f)
                        translationY = scrollProgress * 30f
                    },
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Find Amazing events\nhappening around you.",
                    fontSize = (28f - (scrollProgress * 3f)).sp, // ✅ REFINED font scaling for better sync
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = (34f - (scrollProgress * 4f)).sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // ✅ REFINED search bar - Better sync with header collapse
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .graphicsLayer {
                            scaleX = 1f - (scrollProgress * 0.15f)
                            scaleY = 1f - (scrollProgress * 0.15f)
                            alpha = searchContentAlpha
                        },
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "search for an event...",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )

                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = Color(0xFF424242)
                        ) {
                            IconButton(
                                onClick = onSearchClick,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFFE57373),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}