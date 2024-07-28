package com.example.smartmoviemock.viewmodel.state

import com.example.smartmoviemock.data.model.Movie

sealed class MovieDetailLoadingState {
    data class Success(
        val movie: Movie
    ) : MovieDetailLoadingState()

    data object Loading: MovieDetailLoadingState()

    data class Error(val errorMessage: String) : MovieDetailLoadingState()
}