package com.interstellar.rahulpihujetpackdemo.basicUI

import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star

import androidx.compose.ui.Modifier
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithMenu() {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = { Text("My App") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Open") },
                    onClick = {
                        expanded = false
                        Toast.makeText(context, "Open clicked", Toast.LENGTH_SHORT).show()
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = { Text("Save") },
                    onClick = {
                        expanded = false
                        Toast.makeText(context, "Save clicked", Toast.LENGTH_SHORT).show()
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Star, contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        expanded = false
                        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                    },
                    leadingIcon = {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                    }
                )
            }
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ToolbarWithMenuPreview() {
    RahulPihuJetPackDemoTheme {

        ToolbarWithMenu()
    }
}