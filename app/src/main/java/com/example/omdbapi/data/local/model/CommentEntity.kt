package com.example.omdbapi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = CommentEntity.TABLE_NAME )
data class CommentEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val imdbID: String,
    val comment:String
    ){
    companion object {
        const val TABLE_NAME = "comment"
    }
}