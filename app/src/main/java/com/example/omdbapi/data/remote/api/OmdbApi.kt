package com.example.omdbapi.data.remote.api

import com.example.omdbapi.data.remote.model.MovieDto
import com.example.omdbapi.data.remote.model.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
   //apiService.getMovie(movieTitle,"35ba1dc9")

    @GET("/")
    suspend fun searchForMovies(
        @Query("s") movie_name: String,
        @Query("page") page: Int,
        @Query("apikey") api_key: String
    ): MoviesDto
    @GET("/")
    suspend fun getMovieById(
        @Query("i") imdbID:String,
        @Query("apikey") api_key: String
    ): MovieDto
}