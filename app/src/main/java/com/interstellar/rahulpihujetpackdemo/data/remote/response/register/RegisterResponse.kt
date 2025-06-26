package com.interstellar.rahulpihujetpackdemo.data.remote.response.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerialName("MasterData")
    val masterData: RegisterData,

    @SerialName("Message")
    val message: String,

    @SerialName("Status")
    val status: String,

    @SerialName("StatusNo")
    val statusNo: Int
)