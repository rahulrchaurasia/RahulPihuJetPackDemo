package com.interstellar.rahulpihujetpackdemo.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val mobile: String,
    val password: String
)
