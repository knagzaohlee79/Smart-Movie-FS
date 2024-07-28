package com.example.smartmoviemock.ui.fragment.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.extention.goToComponent
import com.example.smartmoviemock.ui.adapter.MovieAdapter
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.viewmodel.HomeScreenViewModel
import com.example.smartmoviemock.viewmodel.UpComingFragmentViewModel
import com.example.smartmoviemock.viewmodel.state.HomeScreenMovieState
import com.example.smartmoviemock.viewmodel.state.MovieLoadingState
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.FragmentUpComingBinding
import com.example.smartmoviemock.utility.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpComingFragment : BaseFragment<FragmentUpComingBinding>() {
    private lateinit var movieAdapter: MovieAdapter
    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()
    private val upComingFragmentViewModel: UpComingFragmentViewModel by viewModels()

    private val listMovies: ArrayList<Movie> = arrayListOf()
    private var loadMoreJob: Job? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpComingBinding {
        return FragmentUpComingBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setObserver() {
        homeScreenViewModel.homeScreenMovieState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeScreenMovieState.Success -> {
                    handleLoadingView(false)
                    handleErrorView(false)

                    listMovies.clear()
                    listMovies.addAll(state.upComingMovies)
                    movieAdapter.submitList(listMovies)
                }
                is HomeScreenMovieState.Loading -> {
                    // Handle loading state
                    handleErrorView(false)
                    handleLoadingView(true)
                }
                is HomeScreenMovieState.Error -> {
                    // Handle error state
                    handleLoadingView(false)
                    handleErrorView(true, state.errorMessage)
                }
                else -> {

                }
            }
        }

        homeScreenViewModel.isGridLayout.observe(viewLifecycleOwner) { isGrid ->
            switchRecyclerViewLayout(isGrid)
        }

        upComingFragmentViewModel.upComingMovieState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieLoadingState.Success -> {
                    handleLoadingView(false)
                    handleErrorView(false)

                    listMovies.clear()
                    listMovies.addAll(state.movies)
                    movieAdapter.submitList(listMovies)
                }
                is MovieLoadingState.Loading -> {
                    // Handle loading state
                    handleErrorView(false)
                    handleLoadingView(true)
                }
                is MovieLoadingState.Error -> {
                    // Handle error state
                    handleLoadingView(false)
                    handleErrorView(true, state.errorMessage)
                }
                else -> {

                }
            }
        }

        upComingFragmentViewModel.loadMoreMovieState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieLoadingState.Success -> {
                    listMovies.addAll(state.movies)
                    movieAdapter.submitList(listMovies)
                    setLoadMoreComplete()
                }

                is MovieLoadingState.Loading -> {
                    // Handle loading state
                    handleLoadMoreView(true)
                }

                is MovieLoadingState.Error -> {
                    // Handle error state
                    handleLoadMoreView(false)
                    setLoadMoreComplete()
                }

                else -> {

                }
            }

        }
    }

    override fun initView() {
        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.blue_main
            )
        )
        initRecyclerView()
    }

    override fun initListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            upComingFragmentViewModel.fetchUpComingMovies()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.textTryAgain.setOnClickListener {
            upComingFragmentViewModel.fetchUpComingMovies()
        }

        binding.nestedScrollViewMovies.setOnLoadMoreListener {
            upComingFragmentViewModel.loadMoreUpComingMovies()
        }
    }

    override fun initViewModel() {
        //init viewmodel
    }

    override fun initData() {

    }

    private fun switchRecyclerViewLayout(isGridLayout: Boolean) {
        movieAdapter.isGridLayout = isGridLayout

        if (isGridLayout) {
            val numberOfRows = 2
            binding.recyclerViewMovies.apply {
                layoutManager = GridLayoutManager(
                    requireContext(),
                    numberOfRows
                )
            }
        } else {
            binding.recyclerViewMovies.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter(
            itemClickListener = { movie ->
                goToComponent(
                    R.id.action_discoverFragment_to_movieDetailFragment,
                    Bundle().apply {
                        putInt(Constant.MOVIE_ID, movie.id)
                    }
                )
            },
            favoriteButtonClickListener = {
                homeScreenViewModel.insertMovieRoom(it)
            }
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

    private fun handleLoadMoreView(isLoadingMore: Boolean) {
        if (isLoadingMore) {
            binding.layoutLoadMore.visibility = View.VISIBLE
        } else {
            binding.layoutLoadMore.visibility = View.GONE
        }
    }

    private fun setLoadMoreComplete() {
        loadMoreJob = viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            handleLoadMoreView(false)
            delay(1000)
            binding.nestedScrollViewMovies.setLoadingComplete()
        }
    }

    override fun onStop() {
        super.onStop()
        loadMoreJob?.cancel()
    }
}