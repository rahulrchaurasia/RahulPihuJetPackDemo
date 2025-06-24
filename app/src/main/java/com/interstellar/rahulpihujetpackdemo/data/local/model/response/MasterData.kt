package com.interstellar.rahulpihujetpackdemo.data.local.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterData(
    @SerialName("UId")
    val userId: String,
    
    @SerialName("FullName") 
    val fullName: String,
    
    @SerialName("EmailID")
    val emailId: String,
    
    @SerialName("MobileNumber")
    val mobileNumber: String,
    
    @SerialName("EMPType")
    val empType: String
)