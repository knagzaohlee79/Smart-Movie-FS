package com.example.smartmoviemock.ui.adapter

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smartmoviemock.data.model.FragmentInfo
import com.example.smartmoviemock.ui.fragment.discover.AllMoviesFragment
import com.example.smartmoviemock.R

class DiscoverViewPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    private var fragments: MutableMap<String, Fragment> = mutableMapOf()
    private val moviesFragment = AllMoviesFragment()

    init {
        val fragmentName = fragment.context?.let { ContextCompat.getString(it, R.string.movies) }
        fragmentName?.let {
            fragments[it] = moviesFragment
        }
    }

    fun setFragments(fragments: List<FragmentInfo>) {
        fragments.forEach {
            if (!this.fragments.containsKey(it.title)) {
                this.fragments[it.title] = it.fragment
            }
        }
        this.fragments
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            moviesFragment
        } else {
            fragments.values.elementAt(position)
        }
    }
}