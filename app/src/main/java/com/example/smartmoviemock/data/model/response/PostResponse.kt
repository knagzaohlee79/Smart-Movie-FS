package com.example.smartmoviemock.data.model.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
)