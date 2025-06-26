package com.interstellar.rahulpihujetpackdemo.presentation.util


 fun isValidEmail(email: String): Boolean {
    return email.contains("@") && email.contains(".") && email.length >= 5
}

 fun isValidDOB(dob: String): Boolean {
    if (dob.length != 10) return false
    val parts = dob.split("-")
    if (parts.size != 3) return false

    val day = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val year = parts[2].toIntOrNull() ?: return false

    return day in 1..31 && month in 1..12 && year in 1900..2024
}