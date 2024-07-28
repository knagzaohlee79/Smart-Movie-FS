package com.example.smartmoviemock.utility

object Constant {

    const val THEMOVIEDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val THEMOVIEDB_API_KEY = "b6eca7e2615f629baaf865adcc0e1ab1"
    const val THEMOVIEDB_ACCESS_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNmVjYTdlMjYxNWY2MjliYWFmODY1YWRjYzBlMWFiMSIsIm5iZiI6MTcxOTcxMzQ4NS4xMTc3MjcsInN1YiI6IjY2ODAyYjM3MDYxMDdiYWZlNGE4NTE3ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KDuCHVdl-JKad8XwqAdLE3bcF_SmLaP0iW_lA8C7s5o"
    const val THEMOVIEDB_GET_POPULAR = "movie/popular"
    const val THEMOVIEDB_GET_TOP_RATED = "movie/top_rated"
    const val THEMOVIEDB_GET_UPCOMING = "movie/upcoming"
    const val THEMOVIEDB_GET_NOW_PLAYING = "movie/now_playing"
    const val THEMOVIEDB_GET_DETAILS = "movie/{id}"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val THEMOVIEDB_ACCOUNT_ID = "21355860"
    const val THEMOVIEDB_GET_FAVORITE_MOVIES = "account/{account_id}/favorite/movies"
    const val THEMOVIEDB_ADD_FAVORITE_MOVIE = "account/{account_id}/favorite"
    const val THEMOVIEDB_LANGUAGE = "en-US"
    const val THEMOVIEDB_SORT_ASD = "created_at.asc"
    const val BEARER = "Bearer "
    const val THEMOVIEDB_SEARCH_MOVIE = "search/movie"
    const val THEMOVIEDB_GET_MOVIE_GENRES = "genre/movie/list"
    const val THEMOVIEDB_GET_DISCOVER = "discover/movie"
    const val THEMOVIEDB_GET_SIMILAR = "movie/{id}/similar"
    const val THEMOVIEDB_GET_CAST = "movie/{id}/credits"

    const val MOVIE_GENRES = "movie_genre"
    const val MOVIE_ID = "movie_id"
    const val UNSET_ID = -1

    const val ROOM_DB = "movies_database"
}