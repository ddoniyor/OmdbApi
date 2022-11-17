package com.example.omdbapi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = FavouriteEntity.TABLE_NAME )
data class FavouriteEntity(
    @PrimaryKey
    val imdbID: String,
    val favourite:Boolean
){
    companion object {
        const val TABLE_NAME = "favourite"
    }
}
