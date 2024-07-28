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
class TopRatedFragmentViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _topRatedMovieState = MutableLiveData<MovieLoadingState>()
    val topRatedMovieState: LiveData<MovieLoadingState>
        get() = _topRatedMovieState

    private var currentPage = 1

    private val maxPage = 10

    private var _loadMoreMovieState = MutableLiveData<MovieLoadingState>()
    val loadMoreMovieState: LiveData<MovieLoadingState>
        get() = _loadMoreMovieState

    fun fetchTopRatedMovies(page: Int = 1) {
        _topRatedMovieState.value = MovieLoadingState.Loading
        viewModelScope.launch {
            try {
                val topRatedMovies = repository.getTopRatedMovies(page)
                val favoriteMovies = repository.getFavoriteMovies()

                val updatedTopRatedMovies = updateListWithFavorite(
                    originalList = topRatedMovies,
                    favoriteMovies = favoriteMovies
                )

                _topRatedMovieState.value = MovieLoadingState.Success(
                    movies = updatedTopRatedMovies
                )

                currentPage = 1
            } catch (e: Exception) {
                _topRatedMovieState.value = MovieLoadingState.Error(e.message.toString())
                Log.e("TopRatedFragmentViewModel", "Error fetching movies data: ${e.message}")
            }
        }
    }

    fun loadMoreTopRatedMovies() {
        currentPage++
        if (currentPage > maxPage) {
            _loadMoreMovieState.value = MovieLoadingState.Error("End of list")
            return
        }

        viewModelScope.launch {
            _loadMoreMovieState.value = MovieLoadingState.Loading
            try {
                val popularMovies = repository.getTopRatedMovies(currentPage)
                val favoriteMovies = repository.getFavoriteMovies()

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