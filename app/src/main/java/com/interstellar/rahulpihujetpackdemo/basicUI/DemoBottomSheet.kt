package com.interstellar.rahulpihujetpackdemo.basicUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interstellar.rahulpihujetpackdemo.R
import com.interstellar.rahulpihujetpackdemo.presentation.theme.RahulPihuJetPackDemoTheme
import com.interstellar.rahulpihujetpackdemo.presentation.util.formatExpiry


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(){

    var expiryRaw by remember { mutableStateOf("") }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current


    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false

    )
    Column(modifier = Modifier.fillMaxWidth()
        .fillMaxHeight()
        .padding(16.dp)
        .padding(bottom = 40.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        OutlinedTextField(
            value = TextFieldValue(
                text = formatExpiry(expiryRaw),
                selection = TextRange(formatExpiry(expiryRaw).length)
            ),
            onValueChange = { input ->
                val digits = input.text.filter { it.isDigit() }

                // Allow max 4 digits (MMYY)
                if (digits.length <= 4) {
                    expiryRaw = digits
                    //onExpiryDateChange(formatExpiry(digits))

                }
            },
            label = { Text("MM/YY") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("12/25") }
        )
        Button(onClick = { showBottomSheet = true }) {
            Text(text = "Open Bottom Sheet")
        }

        if(showBottomSheet){

            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {

                BottomSheetContent(
                    onCancel = { showBottomSheet = false },
                    onContinue = { showBottomSheet = false }
                )
            }
        }

    }


}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PartialBottomSheetPreview() {
    RahulPihuJetPackDemoTheme {
        PartialBottomSheet()
    }
}



@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BottomSheetContentPreview() {
    RahulPihuJetPackDemoTheme {
        BottomSheetContent(
            onCancel = {},
            onContinue = {}
        )
    }
}

@Composable
fun BottomSheetContent(
    onCancel: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image
        Image(
            painter = painterResource(id = R.drawable.car1), // Replace with actual drawable
            contentDescription = "Sheet Image",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "Premium Service",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = "Enjoy top-tier service with our premium plan. Get 24/7 support and exclusive benefits.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = onContinue,
                modifier = Modifier.weight(1f)
            ) {
                Text("Continue")
            }
        }
    }
}
