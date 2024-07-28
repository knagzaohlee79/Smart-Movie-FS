package com.example.smartmoviemock.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies")
data class MovieEntity (
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String,
    val category: List<String> = arrayListOf(),
    val rating: Float,
    val description: String,
    val posterUrl: String,
    var isFavorite: Boolean = false
) : Serializable