package com.example.omdbapi.presentation.movie_list

import com.example.omdbapi.data.remote.model.Search

data class MovieListState(
    val isLoading:Boolean =false,
    val isRefreshing:Boolean = false,
    val movies:List<Search> = emptyList(),
    val error:String = "",

)