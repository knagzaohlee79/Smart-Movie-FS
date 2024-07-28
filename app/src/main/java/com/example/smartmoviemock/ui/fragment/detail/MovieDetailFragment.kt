package com.example.smartmoviemock.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.example.smartmoviemock.data.model.Cast
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.extention.backToPrevious
import com.example.smartmoviemock.ui.compose.MovieDetailScreen
import com.example.smartmoviemock.ui.compose.theme.MainTheme
import com.example.smartmoviemock.viewmodel.MovieDetailViewModel
import com.example.smartmoviemock.viewmodel.state.DetailScreenLoadingState
import com.example.smartmoviemock.databinding.FragmentMovieDetailBinding
import com.example.smartmoviemock.utility.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private var movieId = -1
    private var movie: Movie? = null
    private var cast: List<Cast>? = null
    private var similarMovies: List<Movie>? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailBinding {
        return FragmentMovieDetailBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieId = it.getInt("movie_id")
            if (movie != null && cast != null && similarMovies != null) {
                return
            }
            movieDetailViewModel.fetchMovieDetails(movieId)
        }
    }

    override fun initViewModel() {
    }

    override fun initData() {

    }

    override fun setObserver() {
        movieDetailViewModel.detailScreenLoadingState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailScreenLoadingState.Success -> {
                    movie = state.movie
                    cast = state.cast
                    similarMovies = state.similarMovies

                    movie?.let { movie ->
                        cast?.let { cast ->
                            similarMovies?.let { similarMovies ->
                                handleLoadingView(false)
                                handleErrorView(false)
                                showComposeView(true)
                                initComposeView(movie, cast, similarMovies)
                            }
                        }
                    }
                }

                is DetailScreenLoadingState.Error -> {
                    handleLoadingView(false)
                    showComposeView(false)
                    handleErrorView(true, state.errorMessage)
                }

                is DetailScreenLoadingState.Loading -> {
                    handleErrorView(false)
                    showComposeView(false)
                    handleLoadingView(true)
                }
            }
        }
    }

    override fun initView() {
    }

    private fun fetchMovieDetails(movieId: Int) {
        movieDetailViewModel.fetchMovieDetails(movieId)
    }

    private fun initComposeView(movie: Movie, cast: List<Cast>, similarMovies: List<Movie>) {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainTheme {
                    MovieDetailScreen(
                        viewModel = movieDetailViewModel,
                        movie = movie,
                        cast = cast,
                        similarMovies = similarMovies
                    )
                }
            }
        }
    }

    override fun initListener() {
        binding.textTryAgain.setOnClickListener {
            movieDetailViewModel.fetchMovieDetails(movieId)
        }

        binding.buttonBack.setOnClickListener {
            backToPrevious()
        }
    }

    private fun handleErrorView(isError: Boolean, errorMessage: String = "") {
        binding.textErrorMessage.text = errorMessage
        if (isError) {
            binding.constrainLoadingInfo.visibility = View.VISIBLE
            binding.layoutError.visibility = View.VISIBLE
        } else {
            binding.constrainLoadingInfo.visibility = View.GONE
            binding.layoutError.visibility = View.GONE
        }
    }

    private fun handleLoadingView(isLoading: Boolean) {
        if (isLoading) {
            binding.constrainLoadingInfo.visibility = View.VISIBLE
            binding.layoutLoading.visibility = View.VISIBLE
        } else {
            binding.constrainLoadingInfo.visibility = View.GONE
            binding.layoutLoading.visibility = View.GONE
        }
    }

    private fun showComposeView(isShow: Boolean) {
        if (isShow) {
            binding.composeView.visibility = View.VISIBLE
        } else {
            binding.composeView.visibility = View.GONE
        }
    }
}