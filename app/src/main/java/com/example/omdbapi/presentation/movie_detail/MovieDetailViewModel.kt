package com.example.omdbapi.presentation.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbapi.common.Constants
import com.example.omdbapi.common.Resource
import com.example.omdbapi.data.local.model.CommentEntity
import com.example.omdbapi.data.local.model.FavouriteEntity
import com.example.omdbapi.domain.repository.OmdbRepository
import com.example.omdbapi.domain.use_case.get_movie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel@Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val repository: OmdbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel()  {
    private val _state = mutableStateOf(MovieDetailState())
    val stateApi: State<MovieDetailState> = _state

    var stateComments by mutableStateOf(emptyList<CommentEntity>())

    init {
        savedStateHandle.get<String>("imdbID")?.let { imdbID ->
            getMovie(false,imdbID,Constants.URL_API_KEY)
        }
    }

    private fun getMovie(isRefreshing: Boolean = false, id:String, apiKey:String) {
        getMovieUseCase(id,apiKey).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MovieDetailState(movie = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        MovieDetailState(error = result.message ?: "Unexpected error occurred")
                }
                is Resource.Loading -> {
                    if (isRefreshing) {
                        _state.value = MovieDetailState(isRefreshing = true)
                    } else {
                        _state.value = MovieDetailState(isLoading = true)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getComments(imdbID:String){
        viewModelScope.launch {
            try {
                repository.getComments(imdbID).collect { response ->
                    stateComments = response
                    println("Received $response getComments")
                }
            } catch (e: Exception) {
                println("The flow has thrown an exception: $e")
            }
        }
    }
    fun deleteComment(commentEntity: CommentEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteComment(commentEntity)
        }
    }

    fun addComment(commentEntity: CommentEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addComment(commentEntity)
        }
    }
}