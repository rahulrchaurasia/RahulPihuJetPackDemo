package com.interstellar.rahulpihujetpackdemo.presentation.components.customNavigationBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.Dest


import com.interstellar.rahulpihujetpackdemo.presentation.components.customNavigationBar.CustomBottomNavigationBar
import com.interstellar.rahulpihujetpackdemo.presentation.components.customNavigationBar.model.BottomNavItem

@Composable
fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 300),
        label = "iconColor"
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "scale_animation"
    )

    Column(
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = null, // ✅ No ripple effect
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .scale(animatedScale),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = animatedColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.label,
            color = animatedColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Selection indicator
//        AnimatedVisibility(
//            visible = isSelected,
//            enter = scaleIn(animationSpec = tween(200)) + fadeIn(animationSpec = tween(200)),
//            exit = scaleOut(animationSpec = tween(200)) + fadeOut(animationSpec = tween(200))
//        ) {
//            Box(
//                modifier = Modifier
//                    .padding(top = 2.dp)
//                    .size(width = 16.dp, height = 2.dp)
//                    .background(
//                        color = MaterialTheme.colorScheme.primary,
//                        shape = RoundedCornerShape(1.dp)
//                    )
//            )
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavItemViewPreview() {
    MaterialTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Selected item
            BottomNavItemView(
                item = BottomNavItem(
                    icon = Icons.Default.Home,
                    label = "Home",
                    destination = Dest.Home // ✅ Fixed - Added proper destination
                ),
                isSelected = true,
                onClick = {}
            )

            // Unselected item
            BottomNavItemView(
                item = BottomNavItem(
                    icon = Icons.Default.Person, // ✅ Changed from Alarm to Person for Profile
                    label = "Profile",
                    destination = Dest.Profile // ✅ Fixed - Added proper destination
                ),
                isSelected = false,
                onClick = {}
            )

            // Additional preview items
            BottomNavItemView(
                item = BottomNavItem(
                    icon = Icons.Default.FavoriteBorder,
                    label = "WishList",
                    destination = Dest.WishList
                ),
                isSelected = false,
                onClick = {}
            )

            BottomNavItemView(
                item = BottomNavItem(
                    icon = Icons.Default.ShoppingCart,
                    label = "Cart",
                    destination = Dest.Cart
                ),
                isSelected = false,
                onClick = {}
            )
        }
    }
}
