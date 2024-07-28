package com.example.smartmoviemock.viewmodel.state

import com.example.smartmoviemock.utility.Constant

data class MovieByGenreUIState (
    val isBackFromAnother: Boolean = false,
    val genreId: Int = Constant.UNSET_ID,
)