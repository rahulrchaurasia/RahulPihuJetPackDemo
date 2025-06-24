package com.interstellar.rahulpihujetpackdemo.data.local.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val mobile: String,
    val password: String
)
