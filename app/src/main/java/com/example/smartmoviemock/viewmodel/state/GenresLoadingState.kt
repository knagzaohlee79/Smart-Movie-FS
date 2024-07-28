package com.example.smartmoviemock.viewmodel.state

import com.example.smartmoviemock.data.model.MovieGenre

sealed class GenresLoadingState {
    data class Success(
        val genres: List<MovieGenre> = emptyList(),
    ) : GenresLoadingState()

    data object Loading: GenresLoadingState()

    data class Error(val errorMessage: String) : GenresLoadingState()
}