package com.example.smartmoviemock.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmoviemock.data.model.MovieGenre
import com.example.smartmoviemock.utility.Constant
import com.bumptech.glide.Glide
import com.example.smartmoviemock.databinding.ItemGenreBinding

class MovieGenreAdapter(
    private val listGenres: List<MovieGenre>,
    private val onItemClicked: (MovieGenre) -> Unit
) : RecyclerView.Adapter<MovieGenreAdapter.MovieGenreViewHolder>() {


    inner class MovieGenreViewHolder(
        private val binding: ItemGenreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieGenre: MovieGenre) {
            binding.textGenreName.text = movieGenre.name
            binding.root.setOnClickListener {
                onItemClicked(movieGenre)
            }

            // Load genre image
            if (movieGenre.genreImagePath.isNotEmpty()) {
                val realUrl = Constant.IMAGE_BASE_URL + movieGenre.genreImagePath
                Glide.with(binding.root.context).load(realUrl).into(binding.imagePoster )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        return MovieGenreViewHolder(
            binding = ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listGenres.size
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bind(listGenres[position])
    }
}