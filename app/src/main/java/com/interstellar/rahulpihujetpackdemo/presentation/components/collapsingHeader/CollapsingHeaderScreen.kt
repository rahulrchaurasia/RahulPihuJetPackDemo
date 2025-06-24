@file:OptIn(ExperimentalMaterial3Api::class)

package com.interstellar.rahulpihujetpackdemo.presentation.components.collapsingHeader

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun CollapsingHeaderScreen(onBackClick: () -> Unit, onSearchClick: () -> Unit) {
    val lazyListState = rememberLazyListState()
    val firstItemOffset = lazyListState.firstVisibleItemScrollOffset
    val headerHeightRange = 224.dp

    val maxOffset = with(LocalDensity.current) { headerHeightRange.toPx() }
    val percentage = (firstItemOffset / maxOffset).coerceIn(0f, 1f)

    val isCollapsed = remember { derivedStateOf { percentage >= 1f } }

    val headerHeight by animateDpAsState(
        targetValue = 280.dp - (224.dp * percentage),
        animationSpec = tween(250)
    )
    val titleAlpha by animateFloatAsState(
        targetValue = if (isCollapsed.value) 1f else 0f,
        animationSpec = tween(250)
    )
    val searchBarAlpha by animateFloatAsState(
        targetValue = if (!isCollapsed.value) 1f else 0f,
        animationSpec = tween(250)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(top = 280.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(20) { index ->
                TransactionItem(
                    title = "Transaction #$index",
                    subtitle = "Subtitle here",
                    amount = "$${(index + 1) * 10}"
                )
            }
        }

        OptimizedCollapsingHeader(
            height = headerHeight,
            isCollapsed = isCollapsed.value,
            titleAlpha = titleAlpha,
            searchContentAlpha = searchBarAlpha,
            onBackClick = onBackClick,
            onSearchClick = onSearchClick
        )
    }
}

@Composable
private fun OptimizedCollapsingHeader(
    height: Dp,
    isCollapsed: Boolean,
    titleAlpha: Float,
    searchContentAlpha: Float,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
//            .background(
//                Color(0xFF26A69A).copy(alpha = 1f - backgroundProgress)
//            )
    ) {
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
                    tint = if (isCollapsed) Color.Black else Color.White
                )
            }

            Text(
                text = "Transactions",
                modifier = Modifier
                    .weight(1f)
                    .alpha(titleAlpha),
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                color = if (isCollapsed) Color.Black else Color.White
            )

            AnimatedVisibility(visible = isCollapsed) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Black
                    )
                }
            }
        }

        AnimatedVisibility(visible = !isCollapsed) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Find Amazing events\nhappening around you.",
                    fontSize = 28.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 34.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
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
                            IconButton(onClick = onSearchClick) {
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

@Composable
private fun TransactionItem(title: String, subtitle: String, amount: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation( 2.dp )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
            }
            Text(text = amount, color = Color(0xFF26A69A), fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}
