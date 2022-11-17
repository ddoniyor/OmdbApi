package com.example.omdbapi.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.omdbapi.data.local.db.AppDatabase.Companion.DB_VERSION
import com.example.omdbapi.data.local.model.CommentEntity
import com.example.omdbapi.data.local.model.FavouriteEntity

@Database(entities = [FavouriteEntity::class, CommentEntity::class], version = DB_VERSION, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    companion object {
        const val DB_VERSION = 1
        const val NAME = "omdb.db"
    }
    abstract fun omdbDao() : OmdbDao
}