package com.example.smartmoviemock.di

import android.content.Context
import androidx.room.Room
import com.example.smartmoviemock.data.local.MovieDao
import com.example.smartmoviemock.data.local.MovieDatabase
import com.example.smartmoviemock.data.network.TheMovieDBAPIService
import com.example.smartmoviemock.data.repository.MovieRepositoryImpl
import com.example.smartmoviemock.repository.MovieRepository
import com.example.smartmoviemock.utility.Constant
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val gson = GsonBuilder()
        .create()

    @Provides
    @Singleton
    fun provideMovieApi(): TheMovieDBAPIService {
        return Retrofit.Builder()
            .baseUrl(Constant.THEMOVIEDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TheMovieDBAPIService::class.java)

    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }


    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: TheMovieDBAPIService,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(movieApi, movieDao)
    }
}