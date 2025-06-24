package com.interstellar.rahulpihujetpackdemo.basicUI

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.interstellar.rahulpihujetpackdemo.R
import com.interstellar.rahulpihujetpackdemo.presentation.components.ImageDialog
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme

@Composable
fun ImageDialogDemo() {

    var showDialog by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }
    val image = painterResource(id = R.drawable.car3)

    RahulPihuJetPackDemoTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

           Column( horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center){


               Button(onClick = { showDialog = true }) {
                   Text("Open Dialog")
               }

               Text(text = "Result:${resultText}")
           }


            ImageDialog(
                showDialog = showDialog,
                onCancel = { showDialog = false },
                onConfirm = { result ->
                    showDialog = false
                    resultText = result
                    // Handle confirm action here
                },
                image = image,
                title = "Delete Item",
                message = "Do you really want to delete this item?"
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ImageDialogPreview() {
    RahulPihuJetPackDemoTheme {

        ImageDialogDemo()
    }
}