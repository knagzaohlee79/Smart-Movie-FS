package com.example.smartmoviemock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmoviemock.data.model.MovieGenre
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.viewmodel.state.GenresLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieGenresLoadingState = MutableLiveData<GenresLoadingState>()
    val movieGenresLoadingState: LiveData<GenresLoadingState>
        get() = _movieGenresLoadingState

    fun getMovieGenres() {
        _movieGenresLoadingState.value = GenresLoadingState.Loading
        viewModelScope.launch {
            try {
                val listGenres = movieRepository.getListMovieGenres()

                //get genres image
                val updateMovieGenres = arrayListOf<MovieGenre>()
                listGenres.forEach { genre ->
                    val imagePath = movieRepository.getGereMovieImagePath(genreId = genre.id)
                    updateMovieGenres.add(genre.copy(genreImagePath = imagePath))
                }

                _movieGenresLoadingState.value = GenresLoadingState.Success(
                    genres = updateMovieGenres
                )
            } catch (e: Exception) {
                _movieGenresLoadingState.value = GenresLoadingState.Error(e.message.toString())
                Log.e("MovieGenreViewModel", "Error fetching movie genres: ${e.message}")
            }
        }
    }
}