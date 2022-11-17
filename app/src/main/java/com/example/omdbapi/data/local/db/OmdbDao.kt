package com.example.omdbapi.data.local.db

import androidx.room.*
import com.example.omdbapi.data.local.model.CommentEntity
import com.example.omdbapi.data.local.model.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OmdbDao {
    @Update(entity = FavouriteEntity::class)
    fun updateFavourite(favourite: FavouriteEntity)

    @Query("SELECT * FROM ${FavouriteEntity.TABLE_NAME} WHERE favourite = 1")
    fun getFavourites():Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavourite(favourite: FavouriteEntity)

    @Delete
    fun deleteFavourite(favourite: FavouriteEntity)



    @Query("SELECT * FROM ${CommentEntity.TABLE_NAME} WHERE imdbID = :imdbID")
    fun getComments(imdbID:String):Flow<List<CommentEntity>>

    @Insert
    fun addComment(comment : CommentEntity)

    @Delete
    fun deleteComment(comment: CommentEntity)

}