package com.example.smartmoviemock.ui.fragment.genre

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmoviemock.data.model.MovieGenre
import com.example.smartmoviemock.extention.goToComponent
import com.example.smartmoviemock.ui.adapter.MovieGenreAdapter
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.viewmodel.MovieGenreViewModel
import com.example.smartmoviemock.viewmodel.state.GenresLoadingState
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.FragmentMovieGenresBinding
import com.example.smartmoviemock.utility.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieGenresFragment : BaseFragment<FragmentMovieGenresBinding>() {

    private val movieGenresViewModel: MovieGenreViewModel by viewModels()
    private lateinit var genreAdapter: MovieGenreAdapter
    private var listGenres: ArrayList<MovieGenre> = arrayListOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieGenresBinding {
        return FragmentMovieGenresBinding.inflate(inflater, container, false)
    }

    private fun fetchGenres() {
        if (listGenres.isEmpty()) {
            movieGenresViewModel.getMovieGenres()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setObserver() {
        movieGenresViewModel.movieGenresLoadingState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GenresLoadingState.Loading -> {
                    handleErrorView(isError = false)
                    handleLoadingView(true)
                }
                is GenresLoadingState.Success -> {
                    handleLoadingView(false)
                    handleErrorView(isError = false)

                    listGenres.clear()
                    listGenres.addAll(state.genres)
                    genreAdapter.notifyDataSetChanged()
                }
                is GenresLoadingState.Error -> {
                    handleLoadingView(false)
                    handleErrorView(state.errorMessage, true)
                } else -> {
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
        genreAdapter = MovieGenreAdapter(
            listGenres = listGenres,
            onItemClicked = { movieGenre ->
                goToComponent(
                    R.id.action_movieGenresFragment_to_moviesByGenreFragment,
                    Bundle().apply {
                        putParcelable(Constant.MOVIE_GENRES, movieGenre)
                    }
                )
            }
        )

        binding.recyclerViewGenres.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun initListener() {
        binding.swipeToRefresh.setOnRefreshListener {
            fetchGenres()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    override fun initViewModel() {
        //init viewmodel
    }

    override fun initData() {
        fetchGenres()
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
}