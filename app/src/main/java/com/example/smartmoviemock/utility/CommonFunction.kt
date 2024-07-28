package com.example.smartmoviemock.utility

import androidx.room.TypeConverter
import com.example.smartmoviemock.data.entity.MovieEntity
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.data.model.response.MovieDetailsResponse
import com.example.smartmoviemock.data.model.MovieGenre
import com.example.smartmoviemock.data.model.response.MovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

object CommonFunction {
    fun getMovieFromMovieResponse(movieResponse: MovieResponse): Movie {
        return Movie(
            id = movieResponse.id,
            title = movieResponse.title,
            releaseDate = movieResponse.releaseDate,
            category = movieResponse.genreIds,
            rating = movieResponse.voteAverage.toFloat(),
            description = movieResponse.overview,
            posterUrl = movieResponse.posterPath.toString()
        )
    }

    fun getMovieFromMovieDetailResponse(movieResponse: MovieDetailsResponse): Movie {
        return Movie(
            id = movieResponse.id,
            title = movieResponse.title,
            releaseDate = movieResponse.releaseDate,
            category = arrayListOf(),
            rating = movieResponse.voteAverage.toFloat(),
            description = movieResponse.overview,
            posterUrl = movieResponse.posterPath.toString(),
            movieGenres = movieResponse.genres.map { it.name },
            originalCountry = movieResponse.originCountry.joinToString(", "),
            originalLanguage = movieResponse.originalLanguage,
            duration = movieResponse.runtime ?: 0
        )
    }

    fun updateListWithFavorite(
        originalList: List<Movie>,
        favoriteMovies: List<Movie>
    ): List<Movie> {
        val favoriteMovieSet = favoriteMovies.map { it.id }.toSet()
        val updatedList = originalList.map { movie ->
            movie.copy(isFavorite = favoriteMovieSet.contains(movie.id))
        }
        return updatedList
    }

    fun updateListSearchWithGenres(listMovies: List<Movie>, genresMap: HashMap<Int, MovieGenre>): List<Movie> {
        val updatedList = arrayListOf<Movie>()
        listMovies.forEach { movie ->
            val listGenreIds = movie.category
            val listGenres = arrayListOf<String>()
            listGenreIds.forEach { genresId ->
                val genre = genresMap[genresId]
                listGenres.add(genre?.name ?: "")
            }
            val updatedMovie = movie.copy(movieGenres = listGenres)
            updatedList.add(updatedMovie)
        }

        return updatedList
    }

    fun getDisplayStringGenres(listGenres: List<String>): String {
        return listGenres.joinToString(" | ")
    }

    fun Movie.toEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            title = this.title,
            releaseDate = this.releaseDate,
            category = this.category.map { it.toString() },
            rating = this.rating,
            description = this.description,
            posterUrl = this.posterUrl,
            isFavorite = this.isFavorite
        )
    }

    fun MovieEntity.toModel(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            releaseDate = this.releaseDate,
            category = this.category.map { it.toInt() },
            movieGenres = emptyList(),
            rating = this.rating,
            description = this.description,
            posterUrl = this.posterUrl,
            isFavorite = this.isFavorite
        )
    }

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListString(list: List<String>): String {
        return Gson().toJson(list)
    }

    val emptyMovie = Movie(
        id = -1,
        title = "",
        releaseDate = "",
        category = listOf(),
        movieGenres = listOf(),
        rating = -1f,
        posterUrl = "",
        description = "",
        isFavorite = false
    )

    fun getFullLanguageName(languageCode: String): String {
        val loc = Locale(languageCode)
        return loc.getDisplayLanguage(loc)
    }

    fun convertMovieDuration(duration: Int): String {
        val hours = duration / 60
        val minutes = duration % 60
        return "${hours}h ${minutes}m"
    }
}