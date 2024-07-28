package com.example.smartmoviemock.data.model.response

import com.example.smartmoviemock.data.model.MovieGenre
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres") val genres: List<MovieGenre>
)
