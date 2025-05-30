package com.interstellar.rahulpihujetpackdemo.domain.model

data class Animal(
    val id: Int,
    val name: String,
    val species: String,
    val habitat: String,
    val diet: String,
    val averageLifespan: String
)



data class AutoCompleteItem(
    val id: Any,
    val displayText: String,
    val searchableText: String = displayText
)
// Validation Result
sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}
