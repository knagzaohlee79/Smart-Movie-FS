package com.example.smartmoviemock.data.model

import com.google.gson.annotations.SerializedName

data class FavorMovieRequestBody (
    @SerializedName("media_type") val mediaType: String = "movie",
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("favorite") val favorite: Boolean
)