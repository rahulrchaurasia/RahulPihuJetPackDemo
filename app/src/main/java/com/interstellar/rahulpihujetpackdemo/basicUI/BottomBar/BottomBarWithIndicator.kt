package com.interstellar.rahulpihujetpackdemo.basicUI.BottomBar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.interstellar.rahulpihujetpackdemo.R

@Composable
fun BottomBarWithIndicator() {
    val tabs = listOf("Home", "Profile", "Settings")
    val icons = listOf(Icons.Default.Home, Icons.Default.Person, Icons.Default.Settings)
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black,
                tonalElevation = 4.dp
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = index == selectedIndex

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedIndex = index },
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = icons[index],
                                    contentDescription = tab,
                                    tint = if (isSelected) Color.Cyan else Color.Gray
                                )
                                AnimatedIndicator(isSelected)
                            }
                        },
                        label = {
                            Text(tab, fontSize = 12.sp)
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Current Tab: ${tabs[selectedIndex]}", fontSize = 20.sp)
        }
    }
}



@Composable
fun AnimatedIndicator(isSelected: Boolean) {
    val height by animateDpAsState(targetValue = if (isSelected) 4.dp else 0.dp, label = "")
    Box(
        modifier = Modifier
            .height(height)
            .width(24.dp)
            .padding(top = 2.dp)
            .background(Color.Cyan, shape = MaterialTheme.shapes.small)
    )
}

@Preview(showBackground = true)
@Composable
fun BottomBarWithIndicatorPreview() {
    MaterialTheme {
        BottomBarWithIndicator()
    }
}