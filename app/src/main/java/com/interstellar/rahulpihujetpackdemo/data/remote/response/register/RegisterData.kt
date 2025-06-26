package com.interstellar.rahulpihujetpackdemo.data.remote.response.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegisterData(

    @SerialName("Message")
    val message: String,

    @SerialName("Result")
    val result: String,

    @SerialName("SavedStatus")
    val savedStatus: Int
)