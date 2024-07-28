package com.example.smartmoviemock.data.model.response

import com.example.smartmoviemock.data.model.Cast
import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<Cast>
)