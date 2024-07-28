package com.example.smartmoviemock.data.model

import java.io.Serializable

data class Movie (
    val id: Int,
    val title: String,
    val releaseDate: String,
    val category: List<Int> = arrayListOf(),
    val movieGenres: List<String> = arrayListOf(),
    val rating: Float,
    val description: String,
    val posterUrl: String,
    var isFavorite: Boolean = false,
    var originalCountry: String = "",
    var originalLanguage: String = "",
    var duration: Int = 0
) : Serializable