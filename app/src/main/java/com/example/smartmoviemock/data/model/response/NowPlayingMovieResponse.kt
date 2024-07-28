package com.example.smartmoviemock.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NowPlayingMovieResponse(
    @SerializedName("dates") val dates: PlayingDateResponse,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : Serializable