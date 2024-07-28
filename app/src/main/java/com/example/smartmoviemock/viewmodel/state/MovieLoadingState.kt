package com.example.smartmoviemock.viewmodel.state

import com.example.smartmoviemock.data.model.Movie

sealed class MovieLoadingState {
    data class Success(
        val movies: List<Movie> = emptyList(),
    ) : MovieLoadingState()

    data object Loading: MovieLoadingState()

    data class Error(val errorMessage: String) : MovieLoadingState()
}