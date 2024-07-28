package com.example.smartmoviemock.ui.fragment.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.smartmoviemock.data.model.FragmentInfo
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.ui.adapter.DiscoverViewPagerAdapter
import com.example.smartmoviemock.viewmodel.HomeScreenViewModel
import com.example.smartmoviemock.viewmodel.state.HomeScreenMovieState
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.FragmentDiscoverBinding
import com.example.smartmoviemock.utility.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>() {

    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()

    private var viewPagerAdapter: DiscoverViewPagerAdapter? = null
    private var listFragmentInfo = arrayListOf<FragmentInfo>()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override fun setObserver() {
        homeScreenViewModel.homeScreenMovieState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeScreenMovieState.Success -> {

                    disableSwitchOrientationButton(true)
                    setupListMovieVisibility(
                        java.util.ArrayList(state.popularMovies),
                        java.util.ArrayList(state.topRatedMovies),
                        java.util.ArrayList(state.upComingMovies),
                        java.util.ArrayList(state.nowPlayingMovies)
                    )

                }

                is HomeScreenMovieState.Loading -> {
                    //setup loading screen
                    disableSwitchOrientationButton(false)
                }

                is HomeScreenMovieState.Error -> {
                    //handle error
                    disableSwitchOrientationButton(false)
                }

                else -> {

                }
            }
        }
    }

    override fun initView() {
        viewPagerAdapter = DiscoverViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter
    }

    override fun initListener() {
        binding.buttonSwitchOrientation.setOnClickListener {
            val currentIsGridLayout = homeScreenViewModel.isGridLayout.value ?: false
            if (currentIsGridLayout) {
                homeScreenViewModel.setIsGridLayout(false)
                binding.buttonSwitchOrientation.setImageResource(R.drawable.ic_grid_blue)
                homeScreenViewModel.setIsGridLayout(false)
            } else {
                binding.buttonSwitchOrientation.setImageResource(R.drawable.ic_portrait)
                homeScreenViewModel.setIsGridLayout(true)
            }
        }
    }

    override fun initViewModel() {
    }

    override fun initData() {
        fetchMoviesData()
    }

    private fun fetchMoviesData() {
        homeScreenViewModel.fetchMoviesData()
    }

    fun goMoviePage(name: String) {
        val position = listFragmentInfo.indexOfFirst { it.title == name }
        binding.viewPager.setCurrentItem(position, true)
    }

    fun disableSwitchOrientationButton(isClickable: Boolean) {
        binding.buttonSwitchOrientation.isClickable = isClickable
    }

    private fun setUpTabLayoutAndViewPager(fragmentInfos: ArrayList<FragmentInfo>) {
        viewPagerAdapter?.setFragments(fragmentInfos)
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.text = fragmentInfos[position].title
        }.attach()

        binding.viewPager.offscreenPageLimit = viewPagerAdapter?.itemCount ?: 1
    }

    private fun setupListMovieVisibility(
        listPopularMovies: java.util.ArrayList<Movie>,
        listTopRatedMovies: java.util.ArrayList<Movie>,
        listUpComingMovies: java.util.ArrayList<Movie>,
        listNowPlayingMovies: java.util.ArrayList<Movie>
    ) {
        listFragmentInfo.clear()
        listFragmentInfo.add(FragmentInfo(getString(R.string.movies), AllMoviesFragment()))

        if (listPopularMovies.isNotEmpty()) {
            listFragmentInfo.add(FragmentInfo(getString(R.string.popular), PopularMovieFragment()))
        }

        if (listTopRatedMovies.isNotEmpty()) {
            listFragmentInfo.add(FragmentInfo(getString(R.string.top_rated), TopRatedFragment()))
        }

        if (listUpComingMovies.isNotEmpty()) {
            listFragmentInfo.add(FragmentInfo(getString(R.string.up_coming), UpComingFragment()))
        }

        if (listNowPlayingMovies.isNotEmpty()) {
            listFragmentInfo.add(
                FragmentInfo(
                    getString(R.string.now_playing),
                    NowPlayingFragment()
                )
            )
        }

        setUpTabLayoutAndViewPager(listFragmentInfo)
    }
}