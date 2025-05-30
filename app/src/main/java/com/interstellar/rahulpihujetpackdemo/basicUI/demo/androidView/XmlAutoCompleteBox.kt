package com.interstellar.rahulpihujetpackdemo.basicUI.demo.androidView

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import android.view.LayoutInflater
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.interstellar.rahulpihujetpackdemo.R
import com.interstellar.rahulpihujetpackdemo.basicUI.demo.ui.AutoCompleteManager

@Composable
fun XmlAutoCompleteBox(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var autoCompleteManager by remember { mutableStateOf<AutoCompleteManager?>(null) }

    AndroidView(
        factory = { ctx ->
            val view = LayoutInflater.from(ctx).inflate(R.layout.autocomplete_layout, null)

            val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            val helperText = view.findViewById<TextView>(R.id.helperText)
            val selectedCard = view.findViewById<MaterialCardView>(R.id.selectedCard)
            val selectedCountryText = view.findViewById<TextView>(R.id.selectedCountryText)

            // Initialize manager
            autoCompleteManager = AutoCompleteManager(
                ctx,
                autoCompleteTextView,
                helperText,
                selectedCard,
                selectedCountryText
            )

            view
        },
        modifier = modifier
    )

    // Cleanup on dispose
    DisposableEffect(Unit) {
        onDispose {
            autoCompleteManager?.cleanup()
        }
    }
}