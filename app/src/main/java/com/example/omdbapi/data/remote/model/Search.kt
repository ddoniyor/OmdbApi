package com.example.omdbapi.data.remote.model

data class Search(
    //url
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String,
    var isFavourite:Boolean?=false
)