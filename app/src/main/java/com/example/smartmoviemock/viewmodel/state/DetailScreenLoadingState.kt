package com.example.smartmoviemock.viewmodel.state

import com.example.smartmoviemock.data.model.Cast
import com.example.smartmoviemock.data.model.Movie

sealed class DetailScreenLoadingState {
    data class Success(
        val movie: Movie,
        val cast: List<Cast>,
        val similarMovies: List<Movie>
    ) : DetailScreenLoadingState()

    data object Loading : DetailScreenLoadingState()

    data class Error(val errorMessage: String) : DetailScreenLoadingState()
}