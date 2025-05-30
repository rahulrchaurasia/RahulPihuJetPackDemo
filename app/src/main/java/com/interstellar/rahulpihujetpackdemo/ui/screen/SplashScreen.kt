package com.interstellar.rahulpihujetpackdemo.ui.screen



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AuthManager
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.router.HomeRoutes
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashComplete()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "ShopApp",
                style = MaterialTheme.typography.headlineLarge
            )
            CircularProgressIndicator()
        }
    }
}