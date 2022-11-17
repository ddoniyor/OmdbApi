package com.example.omdbapi.data.repository

import com.example.omdbapi.data.local.db.AppDatabase
import com.example.omdbapi.data.local.model.CommentEntity
import com.example.omdbapi.data.local.model.FavouriteEntity
import com.example.omdbapi.data.remote.api.OmdbApi
import com.example.omdbapi.data.remote.model.MovieDto
import com.example.omdbapi.data.remote.model.MoviesDto
import com.example.omdbapi.domain.repository.OmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OmdbRepositoryImpl @Inject constructor(
    private val api: OmdbApi,
    private val db: AppDatabase
) : OmdbRepository {
    override suspend fun getMovies(name: String, page: Int, apiKey: String): MoviesDto {
        return api.searchForMovies(name,page,apiKey)
    }

    override suspend fun getMovie(id: String, apiKey: String): MovieDto {
        return api.getMovieById(id,apiKey)
    }

    override suspend fun updateFavourite(favourite: FavouriteEntity) {
        return db.omdbDao().updateFavourite(favourite)
    }

    override suspend fun getFavourites(): Flow<List<FavouriteEntity>> {
        return db.omdbDao().getFavourites()
    }

    override suspend fun addFavourite(favourite: FavouriteEntity) {
        return db.omdbDao().addFavourite(favourite)
    }

    override suspend fun deleteFavourite(favourite: FavouriteEntity) {
        return db.omdbDao().deleteFavourite(favourite)
    }

    override suspend fun getComments(imdbID: String): Flow<List<CommentEntity>> {
        return db.omdbDao().getComments(imdbID)
    }

    override suspend fun addComment(comment: CommentEntity) {
        return db.omdbDao().addComment(comment)
    }

    override suspend fun deleteComment(comment: CommentEntity) {
        return db.omdbDao().deleteComment(comment)
    }

}