package com.interstellar.rahulpihujetpackdemo.presentation.components.customNavigationBar



import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest
import com.interstellar.rahulpihujetpackdemo.presentation.components.customNavigationBar.model.BottomNavItem

/*
1. Individual Indicators Instead of Sliding

Each tab gets its own indicator that appears/disappears
No sliding animation between tabs
Simpler animation logic

 */
// Alternative approach if you prefer your style but want it more reliable
@Composable
fun CustomBottomNavigationBarAlternative(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedSelectedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "selectedIndex"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    // Individual indicator for each item
                    if (index == selectedIndex) {
                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .height(3.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
                                )
                        )
                    }

                    BottomNavItemView(
                        item = item,
                        isSelected = index == selectedIndex,
                        onClick = { onItemSelected(index) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBottomNavigationBarAlternativePreview() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem(Icons.Default.Home, "Home",destination = Dest.Home),
        BottomNavItem(Icons.Default.Search, "WishList",destination = Dest.Home),
        BottomNavItem(Icons.Default.ShoppingCart, "Cart",destination = Dest.Home),
        BottomNavItem(Icons.Default.Person, "Profile",destination = Dest.Home)
    )

    MaterialTheme {
        Column {
            Spacer(modifier = Modifier.weight(1f))

            CustomBottomNavigationBarAlternative(
                items = navItems,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    }
}