package com.interstellar.rahulpihujetpackdemo.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.R
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme

@Composable
fun ImageDialog(
    showDialog: Boolean,
    
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit, // ✅ now returns result string
    image: Painter,
    title: String = "Confirmation",
    message: String = "Are you sure you want to proceed?"
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onCancel() },
            confirmButton = {
                TextButton(onClick = { onConfirm("Data is done") }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text("Cancel")
                }
            },
            title = {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ){

                    Image(painter = image, contentDescription = "Car Image",
                        modifier = Modifier.height(120.dp).padding(bottom = 16.dp))

                    Text(text = title, style = MaterialTheme.typography.titleLarge)
                }


            },

            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            )


    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ImageDialogDemoPreview() {
    RahulPihuJetPackDemoTheme {

        var showDialog = true
        val image = painterResource(id = R.drawable.car2)
        ImageDialog(
            showDialog = showDialog,
            onCancel = { showDialog = false },
            onConfirm = {
                showDialog = false
                // Handle confirm action here
            },
            image = image,
            title = "Delete Item",
            message = "Do you really want to delete this item?"
        )
    }
}