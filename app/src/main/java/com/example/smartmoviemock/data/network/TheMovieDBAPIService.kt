package com.example.smartmoviemock.data.network

import com.example.smartmoviemock.data.model.response.CastResponse
import com.example.smartmoviemock.data.model.FavorMovieRequestBody
import com.example.smartmoviemock.data.model.response.GenresResponse
import com.example.smartmoviemock.data.model.response.ListMoviesResponse
import com.example.smartmoviemock.data.model.response.MovieDetailsResponse
import com.example.smartmoviemock.data.model.response.NowPlayingMovieResponse
import com.example.smartmoviemock.data.model.response.PostResponse
import com.example.smartmoviemock.data.model.response.UpComingMovieResponse
import com.example.smartmoviemock.utility.Constant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBAPIService {

    @GET(Constant.THEMOVIEDB_GET_NOW_PLAYING)
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
        @Query("page") page: Int = 1
    ): Response<NowPlayingMovieResponse>

    @GET(Constant.THEMOVIEDB_GET_POPULAR)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
        @Query("page") page: Int = 1
    ): Response<ListMoviesResponse>

    @GET(Constant.THEMOVIEDB_GET_UPCOMING)
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
        @Query("page") page: Int = 1
    ): Response<UpComingMovieResponse>


    @GET(Constant.THEMOVIEDB_GET_TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
        @Query("page") page: Int = 1
    ): Response<ListMoviesResponse>

    @GET(Constant.THEMOVIEDB_SEARCH_MOVIE)
    suspend fun searchMovieByName(
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
        @Query("query") movieName: String,
        @Query("page") page: Int = 1
    ): Response<ListMoviesResponse>

    @GET(Constant.THEMOVIEDB_GET_DETAILS)
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
    ): Response<MovieDetailsResponse>

    @GET(Constant.THEMOVIEDB_GET_MOVIE_GENRES)
    suspend fun getMovieGenres(
        @Header("Authorization") authorization: String = Constant.BEARER + Constant.THEMOVIEDB_ACCESS_TOKEN,
    ): Response<GenresResponse>

    @GET(Constant.THEMOVIEDB_GET_DISCOVER)
    suspend fun getMovieByGenre(
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ): Response<ListMoviesResponse>

    @GET(Constant.THEMOVIEDB_GET_SIMILAR)
    suspend fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
        @Query("page") page: Int = 1
    ): Response<ListMoviesResponse>

    @GET(Constant.THEMOVIEDB_GET_CAST)
    suspend fun getCastByMovieId(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.THEMOVIEDB_API_KEY,
    ): Response<CastResponse>

    @POST(Constant.THEMOVIEDB_ADD_FAVORITE_MOVIE)
    suspend fun updateFavoriteMovie(
        @Path("account_id") accountId: String = Constant.THEMOVIEDB_ACCOUNT_ID,
        @Header("Authorization") authorization: String = Constant.BEARER + Constant.THEMOVIEDB_ACCESS_TOKEN,
        @Body body: FavorMovieRequestBody,
        @Header("Accept") acceptHeader: String = "application/json",
        @Header("Content-Type") contentTypeHeader: String = "application/json"
    ): Response<PostResponse>

    @GET(Constant.THEMOVIEDB_GET_FAVORITE_MOVIES)
    suspend fun getFavoriteMovies(
        @Header("Authorization") authorization: String = Constant.BEARER + Constant.THEMOVIEDB_ACCESS_TOKEN,
        @Header("Accept") acceptHeader: String = "application/json",
        @Path("account_id") accountId: String = Constant.THEMOVIEDB_ACCOUNT_ID,
        @Query("language") language: String = Constant.THEMOVIEDB_LANGUAGE,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = Constant.THEMOVIEDB_SORT_ASD
    ): Response<ListMoviesResponse>
}