package com.interstellar.rahulpihujetpackdemo.basicUI.demo.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.interstellar.rahulpihujetpackdemo.basicUI.adapter.CountryAutoCompleteAdapter

class AutoCompleteManager(
    private val context: Context,
    private val autoCompleteTextView: AutoCompleteTextView,
    private val helperText: TextView,
    private val selectedCard: MaterialCardView,
    private val selectedCountryText: TextView
) {
    private val countries = listOf(
        "India", "USA", "Germany", "Canada", "UK", "UAE", "Australia",
        "New Zealand", "France", "Italy", "Spain", "Brazil", "Japan",
        "South Korea", "China", "Russia", "Mexico", "Argentina", "Chile",
        "Peru", "Colombia", "South Africa", "Egypt", "Nigeria", "Kenya", "Morocco"
    )

    private val adapter = CountryAutoCompleteAdapter(context, countries)
    private val debounceHandler = Handler(Looper.getMainLooper())
    private var debounceRunnable: Runnable? = null

    init {
        setupAutoComplete()
    }

    private fun setupAutoComplete() {
        // Set adapter
        autoCompleteTextView.setAdapter(adapter)

        // Set threshold
        autoCompleteTextView.threshold = 2

        // Add text watcher with debounce
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Cancel previous debounce
                debounceRunnable?.let { debounceHandler.removeCallbacks(it) }

                // Create new debounce runnable
                debounceRunnable = Runnable {
                    updateHelperText(s.toString())
                    updateSelectedCard(s.toString())
                }

                // Post with 300ms delay
                debounceHandler.postDelayed(debounceRunnable!!, 300)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Handle item selection
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedCountry = adapter.getItem(position) as String
            autoCompleteTextView.setText(selectedCountry)
            updateSelectedCard(selectedCountry)
            updateHelperText(selectedCountry)
        }
    }

    private fun updateHelperText(text: String) {
        helperText.text = when {
            text.length < 2 -> "Type at least 2 characters to search"
            !countries.any { it.contains(text, ignoreCase = true) } -> "No countries found matching '$text'"
            else -> {
                val count = countries.count { it.contains(text, ignoreCase = true) }
                "$count countries found"
            }
        }
    }

    private fun updateSelectedCard(text: String) {
        if (text.isNotEmpty() && countries.contains(text)) {
            selectedCard.visibility = View.VISIBLE
            selectedCountryText.text = text
        } else {
            selectedCard.visibility = View.GONE
        }
    }

    fun cleanup() {
        debounceRunnable?.let { debounceHandler.removeCallbacks(it) }
    }
}