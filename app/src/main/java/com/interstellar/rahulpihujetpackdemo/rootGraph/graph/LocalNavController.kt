package com.interstellar.rahulpihujetpackdemo.rootGraph.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// 3. LocalNavController.kt - For Bottom Navigation
@Composable
fun LocalNavController(): NavHostController {
    return rememberNavController()
}