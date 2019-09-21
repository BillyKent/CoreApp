package me.doapps.core.model

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("message") val message: String
)