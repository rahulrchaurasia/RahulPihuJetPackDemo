package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components.EmptyReceiptContent
import com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components.ReceiptCard
import com.interstellar.rahulpihujetpackdemo.presentation.util.ReceiptUtils.shareReceipt

import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.ReceiptViewModel
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions


// ✅ TOP BAR COMPONENT
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptTopBar(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Receipt",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },

        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewReceiptTopBar() {
    ReceiptTopBar(
        onBackClick = { println("Back pressed") },
        onHomeClick = { println("Home pressed") }
    )
}
