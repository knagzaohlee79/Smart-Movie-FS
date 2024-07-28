package com.example.smartmoviemock.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smartmoviemock.data.entity.MovieEntity
import com.example.smartmoviemock.utility.CommonFunction

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(CommonFunction::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}