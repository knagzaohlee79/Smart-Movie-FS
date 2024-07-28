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
class NowPlayingFragmentViewModel @Inject constructor (
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _nowPlayingMovieState = MutableLiveData<MovieLoadingState>()
    val nowPlayingMovieState: LiveData<MovieLoadingState>
        get() = _nowPlayingMovieState

    private var currentPage = 1

    private val maxPage = 5

    private var _loadMoreMovieState = MutableLiveData<MovieLoadingState>()
    val loadMoreMovieState: LiveData<MovieLoadingState>
        get() = _loadMoreMovieState

    fun fetchNowPlayingMovies(page: Int = 1) {
        _nowPlayingMovieState.value = MovieLoadingState.Loading
        viewModelScope.launch {
            try {
                val nowPlayingMovies = movieRepository.getNowPlayingMovies(page)
                val favoriteMovies = movieRepository.getFavoriteMovies()

                val updatedNowPlayingMovies = updateListWithFavorite(
                    originalList = nowPlayingMovies,
                    favoriteMovies = favoriteMovies
                )

                _nowPlayingMovieState.value = MovieLoadingState.Success(
                    movies = updatedNowPlayingMovies
                )

                currentPage = 1
            } catch (e: Exception) {
                _nowPlayingMovieState.value = MovieLoadingState.Error(e.message.toString())
                Log.e("NowPlayingFragmentViewModel", "Error fetching movies data: ${e.message}")
            }
        }
    }

    fun loadMoreNowPlayingMovies() {
        currentPage++
        if (currentPage > maxPage) {
            _loadMoreMovieState.value = MovieLoadingState.Error("End of list")
            return
        }

        viewModelScope.launch {
            _loadMoreMovieState.value = MovieLoadingState.Loading
            try {
                val popularMovies = movieRepository.getNowPlayingMovies(currentPage)
                val favoriteMovies = movieRepository.getFavoriteMovies()

                val updatedPopularMovies = updateListWithFavorite(
                    originalList = popularMovies,
                    favoriteMovies = favoriteMovies
                )

                _loadMoreMovieState.value = MovieLoadingState.Success(
                    movies = updatedPopularMovies
                )
            } catch (e : Exception) {
                _loadMoreMovieState.value = MovieLoadingState.Error(e.message.toString())
            }
        }
    }
}