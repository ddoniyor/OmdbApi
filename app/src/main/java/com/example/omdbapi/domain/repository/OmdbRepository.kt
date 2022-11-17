package com.example.omdbapi.domain.repository

import com.example.omdbapi.data.local.model.CommentEntity
import com.example.omdbapi.data.local.model.FavouriteEntity
import com.example.omdbapi.data.remote.model.MovieDto
import com.example.omdbapi.data.remote.model.MoviesDto
import kotlinx.coroutines.flow.Flow

interface OmdbRepository {
    suspend fun getMovies(name:String,page:Int,apiKey:String): MoviesDto
    suspend fun getMovie(id: String,apiKey: String): MovieDto

    suspend fun updateFavourite(favourite: FavouriteEntity)
    suspend fun getFavourites(): Flow<List<FavouriteEntity>>
    suspend fun addFavourite(favourite: FavouriteEntity)
    suspend fun deleteFavourite(favourite: FavouriteEntity)

    suspend fun getComments(imdbID:String):Flow<List<CommentEntity>>
    suspend fun  addComment(comment : CommentEntity)
    suspend fun deleteComment(comment: CommentEntity)
}