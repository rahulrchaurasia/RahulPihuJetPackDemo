package com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components.EmptyReceiptContent
import com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components.ReceiptCard
import com.interstellar.rahulpihujetpackdemo.presentation.screen.receipt.components.ReceiptTopBar
import com.interstellar.rahulpihujetpackdemo.presentation.util.ReceiptUtils.shareReceipt

import com.interstellar.rahulpihujetpackdemo.presentation.viewmodel.ReceiptViewModel
import com.interstellar.rahulpihujetpackdemo.rootGraph.graph.GlobalNavigationActions
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager


@Composable
fun ReceiptScreen(

    transactionId : String,
    globalActions: GlobalNavigationActions,
    viewModel: ReceiptViewModel = hiltViewModel(), // ✅ Now this is correct

    modifier: Modifier = Modifier
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()
    val cartCount by viewModel.cartCount.collectAsState()

    val context = LocalContext.current
    val scrollState = rememberScrollState() // ✅ this is required

    // ✅ Local state to snapshot receipt once
    val  receiptData = remember { mutableStateOf<ReceiptData?>(null) }

    // Receipt data
//    val receiptData = remember(cartItems, cartTotal, cartCount) {
//
//        viewModel.generateReceiptData()
//    }

    // ✅ Only fetch + clear cart ONCE when screen loads

    LaunchedEffect(transactionId) {
       // viewModel.setTransactionId(transactionId) // ✅ log and store it
        viewModel.transactionId = transactionId
    }
    LaunchedEffect(Unit) {

        if(receiptData.value == null){

            // Snapshot current cart
            val receipt = viewModel.generateReceiptData()
            receiptData.value = receipt
            // Then clear the cart so UI state is frozen
            viewModel.clearCart()  // ✅ clear after snapshot
           // appDataManager.clearCart()
        }
    }


    BackHandler(enabled = true){
        globalActions.navigateToHome()
    }
    Scaffold(
        topBar = {
            ReceiptTopBar(
                onHomeClick = { globalActions.navigateToHome() },
                onBackClick = {globalActions.navigateToHome()}
            )
        },
        floatingActionButton = {
            ShareReceiptFAB(
                onClick = {
                    receiptData.value?.let {
                        shareReceipt(context, it)
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        receiptData.value?.let { receiptData ->
            // ✅ Show receipt if snapshot exists
            ReceiptContent(
                receiptData = receiptData,
                scrollState = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } ?: run {
            // ✅ Show fallback/empty UI if no data is available (rare case)
            EmptyReceiptContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }



}





// ✅ MAIN RECEIPT CONTENT
@Composable
fun ReceiptContent(
    receiptData: ReceiptData,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        ReceiptCard(receiptData = receiptData)

        Spacer(modifier = Modifier.height(80.dp)) // Space for FAB
    }
}


// ✅ SHARE FAB
@Composable
fun ShareReceiptFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share Receipt"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReceiptScreen() {
    MaterialTheme {
        // Mock navigation for preview

        ReceiptScreen(
            transactionId = "RCp909u9",
            globalActions = GlobalNavigationActions(
                navController = NavController(LocalContext.current)
            ) ,
            viewModel = hiltViewModel(),
            modifier = Modifier,

        )
    }
}
