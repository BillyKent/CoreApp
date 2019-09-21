package me.doapps.core.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("email") val email: String,
    @SerializedName("qr_code") val qrCode: String
)