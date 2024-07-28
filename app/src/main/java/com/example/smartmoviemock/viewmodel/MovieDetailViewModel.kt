package com.example.smartmoviemock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.utility.CommonFunction
import com.example.smartmoviemock.viewmodel.state.DetailScreenLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor (
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean> = _isExpanded

    fun setExpanded(expanded: Boolean) {
        _isExpanded.value = expanded
    }

    private val _detailScreenLoadingState =
        MutableLiveData<DetailScreenLoadingState>()
    val detailScreenLoadingState: LiveData<DetailScreenLoadingState>
        get() = _detailScreenLoadingState

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _detailScreenLoadingState.value = DetailScreenLoadingState.Loading
            try {
                val movieDeferred = async { movieRepository.getMovieDetails(movieId) }
                val similarMoviesDeferred = async { movieRepository.getSimilarMovies(movieId) }
                val genresDeferred = async { movieRepository.getMovieGenres() }
                val castDeferred = async { movieRepository.getCastByMovieId(movieId) }

                joinAll(movieDeferred, similarMoviesDeferred, genresDeferred, castDeferred)

                val movie = movieDeferred.await()
                val similarMovies = similarMoviesDeferred.await()
                val mapMovieGenres = genresDeferred.await()
                val updatedSimilarMovies = CommonFunction.updateListSearchWithGenres(similarMovies, mapMovieGenres)
                val cast = castDeferred.await()

                if (movie != null) {
                    _detailScreenLoadingState.value = DetailScreenLoadingState.Success(
                        movie = movie,
                        cast = cast,
                        similarMovies = updatedSimilarMovies
                    )
                } else {
                    Log.e("MovieDetailViewModel", "Movie details is null")
                    _detailScreenLoadingState.value = DetailScreenLoadingState.Error("Movie details is null")
                }
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error fetching movie details: ${e.message}")
                _detailScreenLoadingState.value = DetailScreenLoadingState.Error("Error fetching movie details: ${e.message}")
            }
        }
    }

}