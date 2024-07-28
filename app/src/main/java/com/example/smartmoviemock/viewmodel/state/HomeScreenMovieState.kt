package com.example.smartmoviemock.viewmodel.state

import com.example.smartmoviemock.data.model.Movie

sealed class HomeScreenMovieState {
    data class Success(
        val popularMovies: List<Movie> = emptyList(),
        val topRatedMovies: List<Movie> = emptyList(),
        val upComingMovies: List<Movie> = emptyList(),
        val nowPlayingMovies: List<Movie> = emptyList(),
        val favoriteMovies: List<Movie> = emptyList(),
    ) : HomeScreenMovieState()

    data object Loading: HomeScreenMovieState()

    data class Error(val errorMessage: String) : HomeScreenMovieState()
}