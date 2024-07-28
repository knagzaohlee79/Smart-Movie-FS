package com.example.smartmoviemock.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.utility.Constant
import com.example.smartmoviemock.utility.CommonFunction
import com.bumptech.glide.Glide
import com.example.smartmoviemock.R
import com.example.smartmoviemock.databinding.ItemSearchResultMovieBinding

class SearchMovieAdapter(
    private val onItemClickListener: (Movie) -> Unit = {}
) :
    ListAdapter<Movie, SearchMovieAdapter.SearchMovieViewHolder>(MovieDiffCallback()) {

    inner class SearchMovieViewHolder(private val binding: ItemSearchResultMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textTitle.text = movie.title
            binding.textGenres.text = CommonFunction.getDisplayStringGenres(movie.movieGenres)
            binding.ratingBar.rating = movie.rating / 2f

            val placeHolderImage = ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.movie_placeholder
            )

            binding.imgPoster.setImageDrawable(placeHolderImage)
            if (movie.posterUrl.isNotEmpty()) {
                val realUrl = Constant.IMAGE_BASE_URL + movie.posterUrl
                placeHolderImage?.let {
                    Glide.with(binding.root.context).load(realUrl).placeholder(it)
                        .into(binding.imgPoster)
                }

                binding.root.setOnClickListener {
                    onItemClickListener(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        return SearchMovieViewHolder(
            ItemSearchResultMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<Movie>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}