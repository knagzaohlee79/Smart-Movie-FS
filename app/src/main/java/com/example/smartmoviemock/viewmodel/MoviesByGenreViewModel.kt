package com.example.smartmoviemock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.utility.CommonFunction.updateListWithFavorite
import com.example.smartmoviemock.viewmodel.state.MovieByGenreUIState
import com.example.smartmoviemock.viewmodel.state.MovieLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _moviesLoadingState = MutableLiveData<MovieLoadingState>()
    val moviesLoadingState: LiveData<MovieLoadingState>
        get() = _moviesLoadingState

    private val _moviesByGenresUIState = MutableLiveData(MovieByGenreUIState())
    var moviesByGenresUIState: MovieByGenreUIState
        get() = _moviesByGenresUIState.value ?: MovieByGenreUIState()
        set(value) {
            _moviesByGenresUIState.value = value
        }

    fun getMoviesByGenre(genreId: Int) {
        _moviesLoadingState.value = MovieLoadingState.Loading
        viewModelScope.launch {
            try {
                val movies = movieRepository.getMoviesByGenre(genreId)
                val favoriteMovies = movieRepository.getFavoriteMovies()

                val updatedPopularMovies = updateListWithFavorite(
                    originalList = movies,
                    favoriteMovies = favoriteMovies
                )
                _moviesLoadingState.value = MovieLoadingState.Success(updatedPopularMovies)
            } catch (e: Exception) {
                _moviesLoadingState.value = MovieLoadingState.Error(e.message.toString())
                Log.e("MoviesByGenreViewModel", "Error fetching movies: ${e.message}")
            }
        }
    }

    fun insertMovieRoom(movie: Movie) {
        viewModelScope.launch {
            movieRepository.insertMovie(movie)
        }
    }
}