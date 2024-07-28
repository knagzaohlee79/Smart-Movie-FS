package com.example.smartmoviemock.viewmodel

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
class PopularFragmentViewModel @Inject constructor (
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _popularMovieState = MutableLiveData<MovieLoadingState>()
    val popularMovieState: LiveData<MovieLoadingState>
        get() = _popularMovieState

    private var currentPage = 1

    private val maxPage = 5
    private var _loadMoreMovieState = MutableLiveData<MovieLoadingState>()
    val loadMoreMovieState: LiveData<MovieLoadingState>
        get() = _loadMoreMovieState

    fun fetchPopularMovies(page: Int = 1) {
        _popularMovieState.value = MovieLoadingState.Loading
        viewModelScope.launch {
            try {
                val popularMovies = movieRepository.getPopularMovies(page)
                val favoriteMovies = movieRepository.getFavoriteMovies()

                val updatedPopularMovies = updateListWithFavorite(
                    originalList = popularMovies,
                    favoriteMovies = favoriteMovies
                )

                _popularMovieState.value = MovieLoadingState.Success(
                    movies = updatedPopularMovies
                )

                currentPage = 1
            } catch (e : Exception) {
                _popularMovieState.value = MovieLoadingState.Error(e.message.toString())
            }
        }
    }

    fun loadMorePopularMovies() {
        currentPage++
        if (currentPage > maxPage) {
            _loadMoreMovieState.value = MovieLoadingState.Error("End of list")
            return
        }

        viewModelScope.launch {
            _loadMoreMovieState.value = MovieLoadingState.Loading
            try {
                val popularMovies = movieRepository.getPopularMovies(currentPage)
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