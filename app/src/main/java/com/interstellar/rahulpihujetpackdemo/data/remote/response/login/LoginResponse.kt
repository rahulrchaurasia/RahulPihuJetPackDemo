package com.interstellar.rahulpihujetpackdemo.data.remote.response.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("Message")
    val message: String,
    
    @SerialName("Status")
    val status: String,
    
    @SerialName("StatusNo")
    val statusNo: Int,
    
    @SerialName("MasterData")
    val masterData: MasterData?
)