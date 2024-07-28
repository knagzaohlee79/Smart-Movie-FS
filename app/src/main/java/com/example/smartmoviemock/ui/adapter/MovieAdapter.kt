package com.example.smartmoviemock.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.utility.Constant
import com.bumptech.glide.Glide
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.ItemMovieGridBinding
import com.example.smartmoviemock.databinding.ItemMovieLandscapeBinding

class MovieAdapter(
    private val isLimitItem: Boolean = false,
    private val itemClickListener: (Movie) -> Unit,
    private val favoriteButtonClickListener: (Movie) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_GRID = 0
        private const val VIEW_TYPE_LINEAR = 1
    }

    private var maxItem = 4

    private var _isGridLayout = false
    var isGridLayout: Boolean
        get() = _isGridLayout
        set(value) {
            _isGridLayout = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LINEAR) {
            val binding = ItemMovieLandscapeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
            MovieViewHolderLandscape(binding)
        } else {
            val binding = ItemMovieGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
            MovieViewHolderPortrait(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.size > maxItem && isLimitItem) 4 else currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        if (holder is MovieViewHolderPortrait) {
            holder.bind(movie)
        } else if (holder is MovieViewHolderLandscape) {
            holder.bind(movie)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_isGridLayout) VIEW_TYPE_GRID else VIEW_TYPE_LINEAR
    }

    override fun submitList(list: List<Movie>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    inner class MovieViewHolderPortrait(private val binding: ItemMovieGridBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textTitle.text = movie.title
            binding.textDate.text = movie.releaseDate

            val placeHolderImage = ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.movie_placeholder
            )
            binding.imgPoster.setImageDrawable(placeHolderImage)
            if (movie.posterUrl.isNotEmpty()) {
                val realUrl = Constant.IMAGE_BASE_URL + movie.posterUrl
                Glide.with(binding.root.context).load(realUrl).into(binding.imgPoster)
            }

            binding.root.setOnClickListener {
                itemClickListener(movie)
            }

            binding.buttonFavorite.isFavorite = movie.isFavorite

            binding.buttonFavorite.setCustomClickListener {
                movie.isFavorite = !movie.isFavorite
                favoriteButtonClickListener(movie)
            }
        }
    }

    inner class MovieViewHolderLandscape(private val binding: ItemMovieLandscapeBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textTitle.text = movie.title
            binding.textDate.text = movie.releaseDate
            binding.textOverview.text = movie.description

            val placeHolderImage = ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.movie_placeholder
            )
            binding.imgPoster.setImageDrawable(placeHolderImage)
            if (movie.posterUrl.isNotEmpty()) {
                val realUrl = Constant.IMAGE_BASE_URL + movie.posterUrl
                Glide.with(binding.root.context).load(realUrl).into(binding.imgPoster)
            }

            binding.root.setOnClickListener {
                itemClickListener(movie)
            }

            binding.buttonFavorite.isFavorite = movie.isFavorite

            binding.buttonFavorite.setCustomClickListener {
                movie.isFavorite = !movie.isFavorite
                favoriteButtonClickListener(movie)
            }
        }
    }
}
