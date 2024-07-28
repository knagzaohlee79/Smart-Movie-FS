package com.example.smartmoviemock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.utility.CommonFunction.updateListWithFavorite
import com.example.smartmoviemock.viewmodel.state.HomeScreenMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _homeScreenMovieState = MutableLiveData<HomeScreenMovieState>()
    val homeScreenMovieState: LiveData<HomeScreenMovieState>
        get() = _homeScreenMovieState

    private val _isGridLayout = MutableLiveData(false)
    val isGridLayout: LiveData<Boolean>
        get() = _isGridLayout

    fun setIsGridLayout(isGridLayout: Boolean) {
        _isGridLayout.value = isGridLayout
    }

    fun fetchMoviesData() {
        _homeScreenMovieState.value = HomeScreenMovieState.Loading
        viewModelScope.launch {
            try {
                val popularMoviesDeferred = async { movieRepository.getPopularMovies() }
                val topRatedMoviesDeferred = async { movieRepository.getTopRatedMovies() }
                val upComingMoviesDeferred = async { movieRepository.getUpcomingMovies() }
                val nowPlayingMoviesDeferred = async { movieRepository.getNowPlayingMovies() }
                val favoriteMoviesDeferred = async { movieRepository.getFavoriteMovies() }

                val (popularMovies, topRatedMovies, upComingMovies, nowPlayingMovies, favoriteMovies) = awaitAll(
                    popularMoviesDeferred,
                    topRatedMoviesDeferred,
                    upComingMoviesDeferred,
                    nowPlayingMoviesDeferred,
                    favoriteMoviesDeferred
                )

                val updatedPopularMovies = updateListWithFavorite(
                    originalList = popularMovies,
                    favoriteMovies = favoriteMovies
                )

                val updatedTopRatedMovies = updateListWithFavorite(
                    originalList = topRatedMovies,
                    favoriteMovies = favoriteMovies
                )

                val updatedUpComingMovies = updateListWithFavorite(
                    originalList = upComingMovies,
                    favoriteMovies = favoriteMovies
                )

                val updatedNowPlayingMovies = updateListWithFavorite(
                    originalList = nowPlayingMovies,
                    favoriteMovies = favoriteMovies
                )

                _homeScreenMovieState.value = HomeScreenMovieState.Success(
                    popularMovies = updatedPopularMovies,
                    topRatedMovies = updatedTopRatedMovies,
                    upComingMovies = updatedUpComingMovies,
                    nowPlayingMovies = updatedNowPlayingMovies,
                    favoriteMovies = favoriteMovies
                )
            } catch (e: Exception) {
                _homeScreenMovieState.value = HomeScreenMovieState.Error(e.message.toString())
                Log.e("HomeScreenViewModel", "Error fetching movies data: ${e.message}")
            }
        }
    }

    fun insertMovieRoom(movie: Movie) {
        viewModelScope.launch {
            movieRepository.insertMovie(movie)
        }
    }
}