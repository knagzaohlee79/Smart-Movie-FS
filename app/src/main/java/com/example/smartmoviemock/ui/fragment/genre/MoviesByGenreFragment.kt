package com.example.smartmoviemock.ui.fragment.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.data.model.MovieGenre
import com.example.smartmoviemock.extention.goToComponent
import com.example.smartmoviemock.ui.adapter.MovieAdapter
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.viewmodel.MoviesByGenreViewModel
import com.example.smartmoviemock.viewmodel.state.MovieLoadingState
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.FragmentMoviesByGenreBinding
import com.example.smartmoviemock.utility.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesByGenreFragment : BaseFragment<FragmentMoviesByGenreBinding>() {
    private val moviesByGenreViewModel: MoviesByGenreViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private val listMovies = arrayListOf<Movie>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoviesByGenreBinding {
        return FragmentMoviesByGenreBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val movieGenre = it.getParcelable<MovieGenre>(Constant.MOVIE_GENRES)
            movieGenre?.id?.let { id ->
                if (!moviesByGenreViewModel.moviesByGenresUIState.isBackFromAnother) {
                    fetchMoviesByGenre(id)
                } else {
                    val movieByGenreUIState = moviesByGenreViewModel.moviesByGenresUIState
                    moviesByGenreViewModel.moviesByGenresUIState =
                        movieByGenreUIState.copy(isBackFromAnother = false)
                }
            }
            movieGenre?.name?.let { name ->
                binding.textToolbarTitle.text = name
            }
        }
    }

    private fun fetchMoviesByGenre(id: Int) {
        moviesByGenreViewModel.getMoviesByGenre(id)
    }

    override fun setObserver() {
        moviesByGenreViewModel.moviesLoadingState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieLoadingState.Loading -> {
                    handleErrorView(isError = false)
                    handleLoadingView(true)
                }

                is MovieLoadingState.Success -> {
                    handleLoadingView(false)
                    handleErrorView(isError = false)

                    listMovies.clear()
                    listMovies.addAll(state.movies)
                    movieAdapter.submitList(listMovies)
                }

                is MovieLoadingState.Error -> {
                    handleLoadingView(false)
                    handleErrorView(state.errorMessage, true)
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }

    override fun initView() {
        binding.swipeToRefresh.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.blue_main
            )
        )

        initRecyclerView()
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter(
            itemClickListener = { movie ->
                goToComponent(
                    R.id.action_moviesByGenreFragment_to_movieDetailFragment,
                    Bundle().apply {
                        putInt(Constant.MOVIE_ID, movie.id)
                    }
                )
            },
            favoriteButtonClickListener = { movie ->
                moviesByGenreViewModel.insertMovieRoom(movie)
            },
            isLimitItem = false
        )

        binding.recyclerViewMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun initListener() {
        binding.swipeToRefresh.setOnRefreshListener {
            val genreId = moviesByGenreViewModel.moviesByGenresUIState.genreId
            if (genreId != Constant.UNSET_ID) {
                fetchMoviesByGenre(genreId)
            }
            binding.swipeToRefresh.isRefreshing = false
        }

        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun initViewModel() {
        //init viewmodel
    }

    override fun initData() {
        val genreId = moviesByGenreViewModel.moviesByGenresUIState.genreId
        if (genreId != Constant.UNSET_ID) {
            fetchMoviesByGenre(genreId)
        }
    }

    private fun handleErrorView(errorMessage: String = "", isError: Boolean) {
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

    override fun onStop() {
        super.onStop()
        moviesByGenreViewModel.moviesByGenresUIState =
            moviesByGenreViewModel.moviesByGenresUIState.copy(isBackFromAnother = true)
    }
}