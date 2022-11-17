package com.example.omdbapi.presentation.movie_detail

import com.example.omdbapi.data.remote.model.MovieDto

data class MovieDetailState(
    val isLoading:Boolean =false,
    val isRefreshing:Boolean = false,
    val movie:MovieDto?=null,
    val error:String = ""
)