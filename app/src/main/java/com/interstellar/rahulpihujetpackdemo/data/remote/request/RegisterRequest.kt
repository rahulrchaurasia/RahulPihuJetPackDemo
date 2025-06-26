package com.interstellar.rahulpihujetpackdemo.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val city: String,
    val company: String,
    val country: String,

    val dob: String,
    val email: String,
    val fullname: String,

    val gender: String,
    @SerialName("marital_status")
    val maritalstatus: String,
    val mobile: String,

    val occupation: String,
    val pincode: String,
    val state: String,

    val street: String
)