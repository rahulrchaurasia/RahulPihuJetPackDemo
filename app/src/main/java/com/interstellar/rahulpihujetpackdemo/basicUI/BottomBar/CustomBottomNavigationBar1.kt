package com.interstellar.rahulpihujetpackdemo.basicUI.BottomBar


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomBottomNavigationBar1() {
    val items = listOf("Wishlist", "Cart", "Profile")
    val icons = listOf(Icons.Default.Favorite, Icons.Default.ShoppingCart, Icons.Default.Person)
    var selectedIndex by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color.Transparent, // No background
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex

            NavigationBarItem(
                selected = isSelected,
                onClick = { selectedIndex = index },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = label,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                        BottomBarIndicator(isSelected)
                    }
                },
                label = {
                    Text(text = label, fontSize = 12.sp)
                },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun BottomBarIndicator(isSelected: Boolean) {
    val height by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 0.dp,
        label = ""
    )
    Box(
        modifier = Modifier
            .height(height)
            .width(24.dp)
            .padding(top = 2.dp)
            .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCustomBottomBar() {
    MaterialTheme {
        Scaffold(
            bottomBar = { CustomBottomNavigationBar1() }
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text("Your Main Content")
            }
        }
    }
}
