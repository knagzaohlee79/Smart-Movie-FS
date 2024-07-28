package com.example.smartmoviemock.ui.fragment.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.extention.goToComponent
import com.example.smartmoviemock.ui.adapter.SearchMovieAdapter
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.viewmodel.SearchScreenViewModel
import com.example.smartmoviemock.viewmodel.state.MovieLoadingState
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.FragmentSearchMovieBinding
import com.example.smartmoviemock.utility.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment<FragmentSearchMovieBinding>() {

    private val searchScreenViewModel: SearchScreenViewModel by viewModels()
    private lateinit var searchMovieAdapter: SearchMovieAdapter

    private val listSearchMovies = arrayListOf<Movie>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchMovieBinding {
        return FragmentSearchMovieBinding.inflate(
            inflater,
            container,
            false
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setObserver() {
        searchScreenViewModel.searchMovieState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieLoadingState.Loading -> {
                    handleErrorView(isError = false)
                    handleNoResultView(false)
                    handleLoadingView(true)
                }

                is MovieLoadingState.Success -> {
                    handleLoadingView(false)
                    handleErrorView(isError = false)

                    if (state.movies.isEmpty()) {
                        handleNoResultView(true)
                    } else {
                        listSearchMovies.clear()
                        listSearchMovies.addAll(state.movies)
                        searchMovieAdapter.submitList(listSearchMovies)
                        if (!isReopen) {
                            binding.recyclerView.scrollToPosition(0)
                        }
                    }
                }

                is MovieLoadingState.Error -> {
                    handleLoadingView(false)
                    handleNoResultView(false)
                    handleErrorView(state.errorMessage, true)
                }

                else -> {

                }
            }
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

    private fun handleNoResultView(isNoResult: Boolean) {
        binding.textResult.text = getString(R.string.no_result)
        if (isNoResult) {
            binding.constrainLoadingInfo.visibility = View.VISIBLE
            binding.layoutNoResult.visibility = View.VISIBLE
        } else {
            binding.constrainLoadingInfo.visibility = View.GONE
            binding.layoutNoResult.visibility = View.GONE
        }
    }

    override fun initView() {
        setupRecyclerView()
    }

    override fun initListener() {
        binding.searchBar.onSearchIconClick = {
            val searchText = binding.searchBar.text
            searchText?.let {
                if (it.isNotEmpty()) {
                    searchScreenViewModel.searchMovieByNameFromAPI(it)
                }
            }
        }

        binding.searchBar.onTypeSearchKey = {
            val searchText = binding.searchBar.text
            searchText?.let {
                if (it.isNotEmpty() && !isReopen) {
                    searchScreenViewModel.searchMovieByNameFromAPI(it)
                } else if (isReopen) {
                    isReopen = false
                }
            }
        }

        binding.searchBar.onCancelClick = {
            showSearchBar(false)
        }

        binding.searchBar.onClearIconClick = {
            binding.constrainLoadingInfo.visibility = View.VISIBLE
            binding.layoutNoResult.visibility = View.VISIBLE
            binding.textResult.text = getString(R.string.search_something)
        }

        binding.buttonSearch.setOnClickListener {
            showSearchBar(true)
        }
    }

    override fun initViewModel() {
        //init viewmodel
    }

    override fun initData() {

    }

    private fun showSearchBar(isShow: Boolean) {
        if (isShow) {
            binding.searchBar.visibility = View.VISIBLE
        } else {
            binding.searchBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        searchMovieAdapter = SearchMovieAdapter(
            onItemClickListener = { movie ->
                parentFragment?.goToComponent(
                    R.id.action_searchMovieFragment_to_movieDetailFragment,
                    Bundle().apply {
                        putInt(Constant.MOVIE_ID, movie.id)
                    }
                )
            },
        )

        binding.recyclerView.apply {
            adapter = searchMovieAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}