package com.example.smartmoviemock.data.repository

import android.util.Log
import com.example.smartmoviemock.data.local.MovieDao
import com.example.smartmoviemock.data.model.Cast
import com.example.smartmoviemock.data.model.Movie
import com.example.smartmoviemock.data.model.MovieGenre
import com.example.smartmoviemock.data.network.TheMovieDBAPIService
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.utility.CommonFunction
import com.example.smartmoviemock.utility.CommonFunction.toEntity
import com.example.smartmoviemock.utility.CommonFunction.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieApi: TheMovieDBAPIService,
    private val movieDao: MovieDao
) : MovieRepository {
    // API calls
    override suspend fun getNowPlayingMovies(page: Int): List<Movie> = withContext(Dispatchers.IO) {
        val response = movieApi.getNowPlayingMovies(page = page)
        val listMovies = arrayListOf<Movie>()

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listMovies.addAll(it.results.map { movieResponse ->
                    CommonFunction.getMovieFromMovieResponse(movieResponse)
                })
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext listMovies
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> = withContext(Dispatchers.IO) {
        val response = movieApi.getPopularMovies(page = page)
        val listMovies = arrayListOf<Movie>()

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listMovies.addAll(it.results.map { movieResponse ->
                    CommonFunction.getMovieFromMovieResponse(movieResponse)
                })
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext listMovies
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> = withContext(Dispatchers.IO) {
        val response = movieApi.getTopRatedMovies(page = page)
        val listMovies = arrayListOf<Movie>()

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listMovies.addAll(it.results.map { movieResponse ->
                    CommonFunction.getMovieFromMovieResponse(movieResponse)
                })
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext listMovies
    }

    override suspend fun getUpcomingMovies(page: Int): List<Movie> = withContext(Dispatchers.IO) {
        val response = movieApi.getUpcomingMovies(page = page)
        val listMovies = arrayListOf<Movie>()

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listMovies.addAll(it.results.map { movieResponse ->
                    CommonFunction.getMovieFromMovieResponse(movieResponse)
                })
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext listMovies
    }

    override suspend fun searchMovieByName(movieName: String): List<Movie> = withContext(Dispatchers.IO) {
        val response = movieApi.searchMovieByName(movieName = movieName)
        val listMovies = arrayListOf<Movie>()

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listMovies.addAll(it.results.map { movieResponse ->
                    CommonFunction.getMovieFromMovieResponse(movieResponse)
                })
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext listMovies
    }

    override suspend fun getMovieDetails(movieId: Int): Movie? = withContext(Dispatchers.IO) {
        val response = movieApi.getMovieDetails(movieId = movieId)
        var movie: Movie? = null

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                movie = CommonFunction.getMovieFromMovieDetailResponse(it)
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext movie
    }

    override suspend fun getMovieGenres(): HashMap<Int, MovieGenre> = withContext(Dispatchers.IO) {
        val response = movieApi.getMovieGenres()
        val movieGenres = hashMapOf<Int, MovieGenre>()

        if (response.isSuccessful) {
            response.body()?.let {
                it.genres.forEach { movieGenre ->
                    movieGenres[movieGenre.id] = movieGenre
                }
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext movieGenres
    }

    override suspend fun getListMovieGenres(): List<MovieGenre> = withContext(Dispatchers.IO) {
        val response = movieApi.getMovieGenres()
        val movieGenres = arrayListOf<MovieGenre>()

        if (response.isSuccessful) {
            response.body()?.let {
                movieGenres.addAll(it.genres)
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext movieGenres
    }

    override suspend fun getMoviesByGenre(genreId: Int): List<Movie> = withContext(Dispatchers.IO) {
        val response = movieApi.getMovieByGenre(genreId = genreId)
        val listMovies = arrayListOf<Movie>()

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listMovies.addAll(it.results.map { movieResponse ->
                    CommonFunction.getMovieFromMovieResponse(movieResponse)
                })
            }
        } else {
            Log.e("API", response.errorBody().toString())
        }

        return@withContext listMovies
    }

    override suspend fun getSimilarMovies(movieId: Int): List<Movie> = withContext(Dispatchers.IO) {
        val response = movieApi.getSimilarMovies(movieId = movieId)
        val listMovies = arrayListOf<Movie>()
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listMovies.addAll(it.results.map { movieResponse ->
                    CommonFunction.getMovieFromMovieResponse(movieResponse)
                })
            }
        }
        return@withContext listMovies
    }

    override suspend fun getCastByMovieId(movieId: Int): List<Cast> = withContext(Dispatchers.IO) {
        val response = movieApi.getCastByMovieId(movieId = movieId)
        val listCast = arrayListOf<Cast>()
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                listCast.addAll(it.cast)
            }
        }

        return@withContext listCast
    }

    override suspend fun getGereMovieImagePath(genreId: Int): String = withContext(Dispatchers.IO) {
        var imagePath = ""
        val listMovie = getMoviesByGenre(genreId)

        if (listMovie.isNotEmpty()) {
            val movie = listMovie.maxBy { it.rating }
            imagePath = movie.posterUrl
        }

        return@withContext imagePath
    }

    //Movies from Room
    override suspend fun insertMovie(movie: Movie) = withContext(Dispatchers.IO) {
        val movieEntity = movie.toEntity()
        movieDao.insertMovie(movieEntity)
    }

    override suspend fun getFavoriteMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val favoriteMoviesEntity = movieDao.getFavoriteMovies()

        val listFavoriteMovies = arrayListOf<Movie>()
        favoriteMoviesEntity.forEach { movieEntity ->
            listFavoriteMovies.add(movieEntity.toModel())
        }

        return@withContext listFavoriteMovies
    }
}