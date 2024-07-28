package com.example.smartmoviemock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.utility.CommonFunction.updateListWithFavorite
import com.example.smartmoviemock.viewmodel.state.MovieLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpComingFragmentViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _upComingMovieState = MutableLiveData<MovieLoadingState>()
    val upComingMovieState: LiveData<MovieLoadingState>
        get() = _upComingMovieState

    private var currentPage = 1

    private val maxPage = 5

    private var _loadMoreMovieState = MutableLiveData<MovieLoadingState>()
    val loadMoreMovieState: LiveData<MovieLoadingState>
        get() = _loadMoreMovieState

    fun fetchUpComingMovies(page: Int = 1) {
        _upComingMovieState.value = MovieLoadingState.Loading
        viewModelScope.launch {
            try {
                val upComingMovies = movieRepository.getUpcomingMovies(page)
                val favoriteMovies = movieRepository.getFavoriteMovies()

                val updatedUpComingMovies = updateListWithFavorite(
                    originalList = upComingMovies,
                    favoriteMovies = favoriteMovies
                )

                _upComingMovieState.value = MovieLoadingState.Success(
                    movies = updatedUpComingMovies
                )

                currentPage = 1
            } catch (e: Exception) {
                _upComingMovieState.value = MovieLoadingState.Error(e.message.toString())
                Log.e("UpComingFragmentViewModel", "Error fetching movies data: ${e.message}")
            }
        }
    }

    fun loadMoreUpComingMovies() {
        currentPage++
        if (currentPage > maxPage) {
            _loadMoreMovieState.value = MovieLoadingState.Error("End of list")
            return
        }

        viewModelScope.launch {
            _loadMoreMovieState.value = MovieLoadingState.Loading
            try {
                val popularMovies = movieRepository.getUpcomingMovies(currentPage)
                val favoriteMovies = movieRepository.getFavoriteMovies()

                val updatedPopularMovies = updateListWithFavorite(
                    originalList = popularMovies,
                    favoriteMovies = favoriteMovies
                )

                _loadMoreMovieState.value = MovieLoadingState.Success(
                    movies = updatedPopularMovies
                )
            } catch (e: Exception) {
                _loadMoreMovieState.value = MovieLoadingState.Error(e.message.toString())
            }
        }
    }
}