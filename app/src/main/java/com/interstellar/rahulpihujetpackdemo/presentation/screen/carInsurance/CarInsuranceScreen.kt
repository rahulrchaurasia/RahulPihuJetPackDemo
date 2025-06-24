@file:OptIn(ExperimentalMaterial3Api::class)

package com.interstellar.rahulpihujetpackdemo.presentation.screen.carInsurance

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.R
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions

// ✅ MAIN CAR INSURANCE SCREEN
@Composable
fun CarInsuranceScreen(
    globalActions: GlobalNavigationActions,
    modifier: Modifier = Modifier,
    onNavigateToCarJourney: (() -> Unit)? = null,
    handleSystemBars: Boolean = true // ✅ NEW: Control system bars handling
) {
    val lazyListState = rememberLazyListState()

    // Product data
    val productData = ProductData(
        title = "Burger",
        price = "20Rs",
        originalPrice = "30Rs",
        discount = "30% Off",
        imageRes = R.drawable.ic_food // Replace with your actual drawable
    )

    // Progressive collapse logic
    val expandedHeaderHeight = 250.dp
    val collapsedHeaderHeight = 56.dp
    val pinnedViewHeight = 48.dp

    // ✅ NEW: Get status bar height
    val statusBarInsets = WindowInsets.statusBars
    val statusBarHeight = with(LocalDensity.current) {
        statusBarInsets.asPaddingValues().calculateTopPadding()
    }

    // ✅ NEW: Calculate actual header heights including status bar
    val actualExpandedHeight = if (handleSystemBars) {
        expandedHeaderHeight + statusBarHeight
    } else {
        expandedHeaderHeight
    }

    val actualCollapsedHeight = if (handleSystemBars) {
        collapsedHeaderHeight + statusBarHeight
    } else {
        collapsedHeaderHeight
    }

    val scrollRange = with(LocalDensity.current) {
        (actualExpandedHeight - actualCollapsedHeight).toPx()
    }

    // Scroll progress calculation
    val scrollProgress by remember {
        derivedStateOf {
            val scrollOffset = lazyListState.firstVisibleItemScrollOffset.toFloat()
            when {
                lazyListState.firstVisibleItemIndex > 0 -> 1f
                scrollOffset > scrollRange -> 1f
                else -> (scrollOffset / scrollRange).coerceIn(0f, 1f)
            }
        }
    }

    // Animated values
    val headerHeight by animateDpAsState(
        targetValue = actualExpandedHeight - ((actualExpandedHeight - actualCollapsedHeight) * scrollProgress),
        animationSpec = tween(durationMillis = 0),
        label = "headerHeight"
    )

    val isCollapsed = scrollProgress >= 0.8f
    val collapsedContentAlpha by animateFloatAsState(
        targetValue = if (isCollapsed) 1f else 0f,
        animationSpec = tween(300),
        label = "collapsedContentAlpha"
    )

    val expandedContentAlpha by animateFloatAsState(
        targetValue = (1f - scrollProgress * 0.8f).coerceIn(0f, 1f),
        animationSpec = tween(50),
        label = "expandedContentAlpha"
    )

    Box(
        modifier = modifier.fillMaxSize()
        // ✅ ALTERNATIVE: Apply window insets padding to the entire screen
            .then(
                if (handleSystemBars) {
                    Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                } else {
                    Modifier
                }
            )
       ) {
        // Main content
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = actualExpandedHeight + pinnedViewHeight + 8.dp
            )
        ) {
            item {
                CarJourneyCard(
                    onStartJourneyClick = {
                        onNavigateToCarJourney?.invoke()
                    }
                )
            }

            // Sample footer content
            items(
                listOf(
                    "Footer 1 Start" to Color(0xFF2196F3),
                    "Footer 2 Start" to Color.Black,
                    "Footer 3 Start\n\nEnd OF Footer3" to Color(0xFF8D6E63),
                    "Center text in box grrg rgjgo3j3\n3jti3jiotj3io4trio3\n3toih3tioh3iot htoi3ht 34t34t3k4pt3p4t 3t3t" to Color(0xFF4CAF50),
                    "Footer 4 Start\n\nEnd OF Footer3" to Color(0xFF8D6E63),
                    "Footer 5 Description\n\nEnd OF Footer3" to Color.Yellow,
                    "Footer 6 Description dwdgw wdwdw iwswisw wdwdiwdwdw k\n\nEnd OF Footer3" to Color.Red,
                    "Footer 7 End OF Footer" to Color.Black,
                )
            ) { (text, color) ->
                Text(
                    text = text,
                    color = color,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                )
            }
        }

        // Pinned discount view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = headerHeight)
        ) {
            PinnedDiscountView(
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

        // Collapsing header
        CarInsuranceCollapsingHeader(
            height = headerHeight,
            isCollapsed = isCollapsed,
            expandedContentAlpha = expandedContentAlpha,
            collapsedContentAlpha = collapsedContentAlpha,
            productData = productData,
            statusBarHeight = if (handleSystemBars) statusBarHeight else 0.dp, // ✅ NEW
            onBackClick = { globalActions.navigateBack() },
            onSearchClick = { /* Handle search */ },
            onShareClick = { /* Handle share */ }
        )
    }
}


// ✅ COLLAPSING HEADER COMPONENT
@Composable
fun CarInsuranceCollapsingHeader(
    height: Dp,
    isCollapsed: Boolean,
    expandedContentAlpha: Float,
    collapsedContentAlpha: Float,
    productData: ProductData,
    statusBarHeight: Dp, // ✅ NEW parameter
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .shadow(elevation = if (isCollapsed) 8.dp else 0.dp)
    ) {
        // Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF26A69A))
        )

        // Expanded content
        if (expandedContentAlpha > 0.01f) {
            ExpandedProductContent(
                productData = productData,
                alpha = expandedContentAlpha,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 56.dp + statusBarHeight, // ✅ NEW: Add status bar height
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
        }

        // Toolbar
        CarInsuranceToolbar(
            isCollapsed = isCollapsed,
            collapsedContentAlpha = collapsedContentAlpha,
            productData = productData,
            statusBarHeight = statusBarHeight, // ✅ NEW: Pass status bar height
            onBackClick = onBackClick,
            onSearchClick = onSearchClick,
            onShareClick = onShareClick
        )
    }
}

// ✅ TOOLBAR COMPONENT
@Composable
fun CarInsuranceToolbar(
    isCollapsed: Boolean,
    collapsedContentAlpha: Float,
    productData: ProductData,
    statusBarHeight: Dp, // ✅ NEW parameter
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp + statusBarHeight) // ✅ NEW: Include status bar height
            .padding(horizontal = 8.dp)
    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(top = statusBarHeight) // ✅ NEW: Add status bar padding
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Collapsed content
        if (isCollapsed) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 48.dp,
                        end = 96.dp,
                        top = statusBarHeight // ✅ NEW: Add status bar padding
                    )
                    .alpha(collapsedContentAlpha),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = productData.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = productData.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = productData.price,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Action buttons
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(top = statusBarHeight) // ✅ NEW: Add status bar padding
        ) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White
                )
            }
        }
    }
}

// ✅ EXPANDED PRODUCT CONTENT
@Composable
fun ExpandedProductContent(
    productData: ProductData,
    alpha: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .alpha(alpha)
            .graphicsLayer {
                translationY = (1f - alpha) * 30f
            },
        verticalAlignment = Alignment.Top
    ) {
        // Product image
        Image(
            painter = painterResource(id = productData.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Product details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = productData.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productData.price,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = productData.originalPrice,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textDecoration = TextDecoration.LineThrough
                )

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    color = Color(0xFF4CAF50),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = productData.discount,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// ✅ PINNED DISCOUNT VIEW
@Composable
fun PinnedDiscountView(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(16.dp),
        color = Color(0xFFE91E63).copy(alpha = 0.9f), // Pink with transparency
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 4.dp
    ) {
        Text(
            text = "Get 40% DISCOUNT",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun CarJourneyCard(
    onStartJourneyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Lets Start Car Journey",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = onStartJourneyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "START JOURNEY",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Accumsan in nisl nisl scelerisque eu. Semper feugiat nibh sed pulvinar proin gravida hendrerit lectus.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


// ✅ DATA CLASS
data class ProductData(
    val title: String,
    val price: String,
    val originalPrice: String,
    val discount: String,
    val imageRes: Int
)

// ✅ PREVIEWS
@Preview(showBackground = true, heightDp = 800)
@Composable
fun CarInsuranceScreenPreview() {
    MaterialTheme {
        // Mock implementation for preview
        Box(modifier = Modifier.fillMaxSize()) {
            Text("CarInsurance Screen - Use with NavController in actual app")
        }
    }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
fun CarInsuranceCollapsingHeaderExpandedPreview() {
    MaterialTheme {
        CarInsuranceCollapsingHeader(
            height = 250.dp,
            isCollapsed = false,
            expandedContentAlpha = 1f,
            collapsedContentAlpha = 0f,
            productData = ProductData(
                title = "Burger",
                price = "20Rs",
                originalPrice = "30Rs",
                discount = "30% Off",
                imageRes = android.R.drawable.ic_menu_gallery
            ),
            onBackClick = { },
            onSearchClick = { },
            onShareClick = { },
            statusBarHeight = 24.dp, // Standard status bar height for preview
            modifier = Modifier.fillMaxWidth() // Standard modifier for header

        )
    }
}

@Preview(showBackground = true, heightDp = 100)
@Composable
fun CarInsuranceCollapsingHeaderCollapsedPreview() {
    MaterialTheme {
        CarInsuranceCollapsingHeader(
            height = 56.dp,
            isCollapsed = true,
            expandedContentAlpha = 0f,
            collapsedContentAlpha = 1f,
            productData = ProductData(
                title = "Burger",
                price = "20Rs",
                originalPrice = "30Rs",
                discount = "30% Off",
                imageRes = android.R.drawable.ic_menu_gallery
            ),
            onBackClick = { },
            onSearchClick = { },
            onShareClick = { },
            statusBarHeight = 24.dp, // Standard status bar height for preview
            modifier = Modifier.fillMaxWidth() // Standard modifier for header

        )
    }
}

@Preview(showBackground = true)
@Composable
fun PinnedDiscountViewPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.LightGray)
        ) {
            PinnedDiscountView(
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandedProductContentPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF26A69A))
                .padding(16.dp)
        ) {
            ExpandedProductContent(
                productData = ProductData(
                    title = "Burger",
                    price = "20Rs",
                    originalPrice = "30Rs",
                    discount = "30% Off",
                    imageRes = android.R.drawable.ic_menu_gallery
                ),
                alpha = 1f
            )
        }
    }
}

// ✅ USAGE IN NAVIGATION:
/*
NavHost(...) {
    composable("car_insurance") {
        CarInsuranceScreen(
            navController = navController,
            onStartJourney = {
                navController.navigate("car_journey")
            }
        )
    }
}
*/