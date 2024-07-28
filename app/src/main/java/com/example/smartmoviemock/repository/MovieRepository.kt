package com.example.smartmoviemock.repository

import com.example.smartmoviemock.data.model.Cast
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.data.model.MovieGenre

interface MovieRepository {
    // API calls
    suspend fun getNowPlayingMovies(page: Int = 1): List<Movie>
    suspend fun getPopularMovies(page: Int = 1): List<Movie>
    suspend fun getTopRatedMovies(page: Int = 1): List<Movie>
    suspend fun getUpcomingMovies(page: Int = 1): List<Movie>
    suspend fun searchMovieByName(movieName: String): List<Movie>
    suspend fun getMovieDetails(movieId: Int): Movie?
    suspend fun getMovieGenres(): HashMap<Int, MovieGenre>
    suspend fun getListMovieGenres(): List<MovieGenre>
    suspend fun getMoviesByGenre(genreId: Int): List<Movie>
    suspend fun getSimilarMovies(movieId: Int): List<Movie>
    suspend fun getCastByMovieId(movieId: Int): List<Cast>
    suspend fun getGereMovieImagePath(genreId: Int): String

    // Room operations
    suspend fun insertMovie(movie: Movie)
    suspend fun getFavoriteMovies(): List<Movie>
}