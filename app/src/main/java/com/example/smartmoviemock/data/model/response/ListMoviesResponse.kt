package com.example.smartmoviemock.data.model.response

import com.google.gson.annotations.SerializedName

data class ListMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieResponse>
)