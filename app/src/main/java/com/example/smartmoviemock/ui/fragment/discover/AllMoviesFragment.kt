package com.example.smartmoviemock.ui.fragment.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.extention.goToComponent
import com.example.smartmoviemock.ui.adapter.MovieAdapter
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.viewmodel.HomeScreenViewModel
import com.example.smartmoviemock.viewmodel.state.HomeScreenMovieState
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.FragmentAllMoviesBinding
import com.example.smartmoviemock.utility.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMoviesFragment : BaseFragment<FragmentAllMoviesBinding>() {

    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()

    private val listPopularMovies: ArrayList<Movie> = arrayListOf()
    private val listTopRatedMovies: ArrayList<Movie> = arrayListOf()
    private val listUpComingMovies: ArrayList<Movie> = arrayListOf()
    private val listNowPlayingMovies: ArrayList<Movie> = arrayListOf()

    private lateinit var popularAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var upComingAdapter: MovieAdapter
    private lateinit var nowPlayingAdapter: MovieAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllMoviesBinding {
        return FragmentAllMoviesBinding.inflate(
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

                    listPopularMovies.clear()
                    listPopularMovies.addAll(state.popularMovies)
                    popularAdapter.submitList(listPopularMovies)

                    listTopRatedMovies.clear()
                    listTopRatedMovies.addAll(state.topRatedMovies)
                    topRatedAdapter.submitList(listTopRatedMovies)

                    listUpComingMovies.clear()
                    listUpComingMovies.addAll(state.upComingMovies)
                    upComingAdapter.submitList(listUpComingMovies)

                    listNowPlayingMovies.clear()
                    listNowPlayingMovies.addAll(state.nowPlayingMovies)
                    nowPlayingAdapter.submitList(listNowPlayingMovies)
                    setupListMovieVisibility(
                        listPopularMovies,
                        listTopRatedMovies,
                        listUpComingMovies,
                        listNowPlayingMovies
                    )

                }

                is HomeScreenMovieState.Loading -> {
                    //setup loading screen
                    handleErrorView(false)
                    handleLoadingView(true)
                    (parentFragment as? DiscoverFragment)?.disableSwitchOrientationButton(false)
                }

                is HomeScreenMovieState.Error -> {
                    //handle error
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
    }

    private fun setupListMovieVisibility(
        listPopularMovies: java.util.ArrayList<Movie>,
        listTopRatedMovies: java.util.ArrayList<Movie>,
        listUpComingMovies: java.util.ArrayList<Movie>,
        listNowPlayingMovies: java.util.ArrayList<Movie>
    ) {
        if (listPopularMovies.isEmpty()) {
            binding.layoutPopular.visibility = View.GONE
        } else {
            binding.layoutPopular.visibility = View.VISIBLE
        }

        if (listTopRatedMovies.isEmpty()) {
            binding.layoutTopRated.visibility = View.GONE
        } else {
            binding.layoutTopRated.visibility = View.VISIBLE
        }

        if (listUpComingMovies.isEmpty()) {
            binding.layoutUpComing.visibility = View.GONE
        } else {
            binding.layoutUpComing.visibility = View.VISIBLE
        }

        if (listNowPlayingMovies.isEmpty()) {
            binding.layoutNowPlaying.visibility = View.GONE
        } else {
            binding.layoutNowPlaying.visibility = View.VISIBLE
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

    private fun switchRecyclerViewLayout(isGridLayout: Boolean) {
        popularAdapter.isGridLayout = isGridLayout
        topRatedAdapter.isGridLayout = isGridLayout
        upComingAdapter.isGridLayout = isGridLayout
        nowPlayingAdapter.isGridLayout = isGridLayout

        val numberOfRows = 2

        if (isGridLayout) {
            binding.recyclerViewPopular.apply {
                layoutManager = GridLayoutManager(
                    requireContext(),
                    numberOfRows
                )
            }

            binding.recyclerViewTopRated.apply {
                layoutManager = GridLayoutManager(
                    requireContext(),
                    numberOfRows
                )
            }

            binding.recyclerViewUpComing.apply {
                layoutManager = GridLayoutManager(
                    requireContext(),
                    numberOfRows
                )
            }

            binding.recyclerViewNowPlaying.apply {
                layoutManager = GridLayoutManager(
                    requireContext(),
                    numberOfRows
                )
            }
        } else {
            binding.recyclerViewPopular.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            binding.recyclerViewTopRated.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            binding.recyclerViewUpComing.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            binding.recyclerViewNowPlaying.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }

    override fun initListener() {
        binding.textSeeAllPopular.setOnClickListener {
            (parentFragment as? DiscoverFragment)?.goMoviePage(getString(R.string.popular))
        }

        binding.textSeeAllTopRated.setOnClickListener {
            (parentFragment as? DiscoverFragment)?.goMoviePage(getString(R.string.top_rated))
        }

        binding.textSeeAllUpComing.setOnClickListener {
            (parentFragment as? DiscoverFragment)?.goMoviePage(getString(R.string.up_coming))
        }

        binding.textSeeAllNowPlaying.setOnClickListener {
            (parentFragment as? DiscoverFragment)?.goMoviePage(getString(R.string.now_playing))
        }

        binding.textTryAgain.setOnClickListener {
            homeScreenViewModel.fetchMoviesData()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            homeScreenViewModel.fetchMoviesData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun initViewModel() {
    }

    override fun initData() {

    }

    private fun initRecyclerView() {
        popularAdapter = MovieAdapter(
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
            },
            isLimitItem = true
        )

        topRatedAdapter = MovieAdapter(
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
            },
            isLimitItem = true
        )

        upComingAdapter = MovieAdapter(
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
            },
            isLimitItem = true
        )

        nowPlayingAdapter = MovieAdapter(
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
            },
            isLimitItem = true
        )

        binding.recyclerViewPopular.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.recyclerViewTopRated.apply {
            adapter = topRatedAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.recyclerViewUpComing.apply {
            adapter = upComingAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.recyclerViewNowPlaying.apply {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}