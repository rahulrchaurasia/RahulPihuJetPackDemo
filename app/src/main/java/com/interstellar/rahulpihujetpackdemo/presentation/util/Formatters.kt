package com.interstellar.rahulpihujetpackdemo.presentation.util

// File: ui/utils/Formatters.kt


fun formatCardNumber(digits: String): String {
    return digits.chunked(4).joinToString(" ")
}

fun formatExpiry(digits: String): String {
    return when {
        digits.length >= 3 -> "${digits.take(2)}/${digits.drop(2)}"
        else -> digits
    }
}

 fun formatDOB(digits: String): String {
    return when {
        digits.length >= 5 -> "${digits.take(2)}-${digits.substring(2, 4)}-${digits.drop(4)}"
        digits.length >= 3 -> "${digits.take(2)}-${digits.drop(2)}"
        else -> digits
    }
}

