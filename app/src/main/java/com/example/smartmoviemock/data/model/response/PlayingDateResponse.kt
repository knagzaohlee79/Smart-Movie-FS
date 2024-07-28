package com.example.smartmoviemock.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlayingDateResponse(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String
) : Serializable
