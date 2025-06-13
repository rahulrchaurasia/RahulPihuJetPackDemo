package com.interstellar.rahulpihujetpackdemo.ui.components.dialog


// ✅ Required imports for CommonAlertDialog.kt
// Core Compose & Material 3
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

// Material Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart

// Foundation & Layout
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

// Runtime & State
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// UI & Graphics
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CommonAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    confirmButtonText: String = "Submit",
    cancelButtonText: String = "Cancel",
    onConfirm: () -> Unit,
    icon: ImageVector? = null
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text(confirmButtonText)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss
                ) {
                    Text(cancelButtonText)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}



@Composable
fun DeleteConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    itemName: String
) {
    CommonAlertDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        title = "Delete Item",
        message = "Are you sure you want to delete \"$itemName\"? This action cannot be undone.",
        confirmButtonText = "Delete",
        cancelButtonText = "Cancel",
        onConfirm = onConfirm,
        icon = Icons.Default.Delete
    )
}

@Composable
fun LogoutConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    CommonAlertDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        title = "Logout",
        message = "Are you sure you want to logout from your account?",
        confirmButtonText = "Logout",
        cancelButtonText = "Cancel",
        onConfirm = onConfirm,
        icon = Icons.Default.ExitToApp
    )
}
@Preview(
    showBackground = true,
    showSystemUi = false
)
@Composable
fun CommonAlertDialogPreview() {
    MaterialTheme {
        CommonAlertDialog(
            showDialog = true,
            onDismiss = {},
            title = "Add to Cart",
            message = "Do you want to add \"iPhone 15 Pro\" (Qty: 2) to your cart for $999?",
            confirmButtonText = "Add to Cart",
            cancelButtonText = "Cancel",
            onConfirm = {},
            icon = Icons.Default.ShoppingCart
        )
    }
}