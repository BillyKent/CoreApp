package me.doapps.core.model

import com.google.gson.annotations.SerializedName

data class Benefit(
    @SerializedName("name") val name: String,
    @SerializedName("serial") val serial: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)