package me.doapps.core.model

import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("name") val name: String,
    @SerializedName("document_url") val docUrl: String
)