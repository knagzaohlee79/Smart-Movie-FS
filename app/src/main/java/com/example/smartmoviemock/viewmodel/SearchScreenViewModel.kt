package com.example.smartmoviemock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.utility.CommonFunction
import com.example.smartmoviemock.viewmodel.state.MovieLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor (
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _searchMovieState = MutableLiveData<MovieLoadingState>()
    val searchMovieState: LiveData<MovieLoadingState>
        get() = _searchMovieState

    fun searchMovieByNameFromAPI(movieName: String) {
        viewModelScope.launch {
            _searchMovieState.value = MovieLoadingState.Loading
            try {
                val listSearchMovies = movieRepository.searchMovieByName(movieName = movieName)
                val mapMovieGenres = movieRepository.getMovieGenres()
                val updatedListMovie = CommonFunction.updateListSearchWithGenres(
                    listSearchMovies,
                    mapMovieGenres
                )
                _searchMovieState.value = MovieLoadingState.Success(
                    movies = updatedListMovie
                )
            } catch (e: Exception) {
                _searchMovieState.value =
                    MovieLoadingState.Error(errorMessage = e.message.toString())
                Log.e("SearchScreenViewModel", "Error: ${e.message}")
            }
        }
    }
}