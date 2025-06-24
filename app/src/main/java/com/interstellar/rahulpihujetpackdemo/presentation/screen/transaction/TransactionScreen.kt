package com.interstellar.rahulpihujetpackdemo.presentation.screen.transaction



import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    globalActions: GlobalNavigationActions,

    modifier: Modifier = Modifier,
    onNavigateToCarInsurance: (() -> Unit)? = null
) {
    val lazyListState = rememberLazyListState()

    // ✅ PROGRESSIVE COLLAPSE LOGIC - Like Material Design
    val expandedHeaderHeight = 280.dp
    val collapsedHeaderHeight = 56.dp
    val scrollRange = with(LocalDensity.current) {
        (expandedHeaderHeight - collapsedHeaderHeight).toPx()
    }

    // ✅ OPTIMIZED with derivedStateOf - Only recomputes when scroll values change
    val scrollProgress by remember {
        derivedStateOf {
            val scrollOffset = lazyListState.firstVisibleItemScrollOffset.toFloat()
            when {
                lazyListState.firstVisibleItemIndex > 0 -> 1f // Fully collapsed if scrolled past first item
                scrollOffset > scrollRange -> 1f // Fully collapsed if scrolled beyond range
                else -> (scrollOffset / scrollRange).coerceIn(0f, 1f) // Progressive collapse
            }
        }
    }

    // ✅ PROGRESSIVE ANIMATIONS - Smooth transitions
    val headerHeight by animateDpAsState(
        targetValue = expandedHeaderHeight - ((expandedHeaderHeight - collapsedHeaderHeight) * scrollProgress),
        animationSpec = tween(durationMillis = 0), // No delay for smooth scrolling
        label = "headerHeight"
    )

    // Title appears only when 90% collapsed (like your XML logic)
    val titleAlpha by animateFloatAsState(
        targetValue = if (scrollProgress >= 0.9f) 1f else 0f,
        animationSpec = tween(200),
        label = "titleAlpha"
    )

    // Search content fades out in sync with header collapse
    val searchContentAlpha by animateFloatAsState(
        targetValue = (1f - scrollProgress * 0.8f).coerceIn(0f, 1f),
        animationSpec = tween(50),
        label = "searchContentAlpha"
    )

    // Background transitions from gradient to white
    val backgroundProgress by animateFloatAsState(
        targetValue = scrollProgress,
        animationSpec = tween(100),
        label = "backgroundProgress"
    )

    // Search icon appears when fully collapsed
    val searchIconAlpha by animateFloatAsState(
        targetValue = if (scrollProgress >= 0.95f) 1f else 0f,
        animationSpec = tween(200),
        label = "searchIconAlpha"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Content
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = expandedHeaderHeight)
        ) {
            item {
                TransactionJourneyCard(
                    onStartJourneyClick = {

                        //Implemet by parent View {delegate/call back handle by parent}
                        //globalActions.navigateTo(Dest.CarInsurance)
                        onNavigateToCarInsurance?.invoke()
                    }
                )
            }

            items(25) { index ->
                TransactionListItem(
                    title = "Transaction ${index + 1}",
                    subtitle = "Lorem ipsum dolor sit amet",
                    amount = "$${(index + 1) * 25}.00"
                )
            }
        }

        // ✅ PROGRESSIVE Collapsing Header
        ProgressiveCollapsingHeader(
            height = headerHeight,
            scrollProgress = scrollProgress,
            titleAlpha = titleAlpha,
            searchContentAlpha = searchContentAlpha,
            backgroundProgress = backgroundProgress,
            searchIconAlpha = searchIconAlpha,
            onBackClick = { globalActions.navigateBack() },
            onSearchClick = { /* Handle search */ }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun FullTransactionScreenPreview() {
    MaterialTheme {
        // Mock navigation for preview

        TransactionScreen(
            globalActions = GlobalNavigationActions(
                navController = NavController(LocalContext.current)
            ) ,
           
            modifier = Modifier)
    }
}



// ✅ Key Features of This Implementation:

/*
🎯 PROGRESSIVE COLLAPSE BEHAVIOR:
- Header gradually shrinks from 280dp to 56dp
- Content fades and scales during scroll
- Background transitions from teal to white
- Icons change color progressively

📱 STANDARD MATERIAL DESIGN:
- Title appears only at 90% collapse (like your XML)
- Search icon appears when fully collapsed (95%)
- Smooth animations without jarring transitions
- Proper elevation changes

🚀 PERFORMANCE OPTIMIZED:
- Uses animationSpec with 0ms for scroll-linked animations
- Conditional rendering for better performance
- Efficient color blending with compositeOver()

📋 FOLLOWS YOUR XML LOGIC:
- percentage >= 0.9f for title appearance
- Progressive scroll progress calculation
- Proper scroll range handling
*/