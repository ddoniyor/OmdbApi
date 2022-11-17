package com.example.omdbapi.presentation.movie_list

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbapi.common.Constants
import com.example.omdbapi.common.Resource
import com.example.omdbapi.data.local.model.FavouriteEntity
import com.example.omdbapi.domain.repository.OmdbRepository
import com.example.omdbapi.domain.use_case.get_movies.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val repository: OmdbRepository
) : ViewModel() {
    private val _state = mutableStateOf(MovieListState())
    val stateApi: State<MovieListState> = _state

    var stateFavourite by mutableStateOf(emptyList<FavouriteEntity>())


    init {
        getMovies("Sherlock",2,Constants.URL_API_KEY)
    }

    fun getMovies(name:String,page:Int,apiKey:String) {
        getMoviesUseCase(name,page,apiKey).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MovieListState(movies = result.data?.Search ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        MovieListState(error = result.message ?: "Unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = MovieListState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun updateFavourite(favourite: FavouriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavourite(favourite)
        }
    }

    fun addFavourite(favourite: FavouriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavourite(favourite)
        }
    }
    fun deleteFavourite(favourite: FavouriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavourite(favourite)
        }
    }

    fun getFavourites(){
        viewModelScope.launch {
            try {
                repository.getFavourites().collect { response ->
                    stateFavourite = response
                    println("Received $response getFavourites")
                }
            } catch (e: Exception) {
                println("The flow has thrown an exception: $e")
            }
        }
    }

}