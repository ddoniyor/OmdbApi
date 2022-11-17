package com.example.omdbapi.presentation.movie_list

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.omdbapi.data.local.model.FavouriteEntity
import com.example.omdbapi.data.remote.model.Search
import com.example.omdbapi.presentation.MovieScreen
import com.example.omdbapi.presentation.movie_list.components.MovieItem

@Composable
fun MovieList(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val state = viewModel.stateApi.value

    LaunchedEffect(Unit) {
        viewModel.getFavourites()
    }

    
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()) {
            items(state.movies) { movie ->

                Log.d("TAG","${viewModel.stateFavourite} $$$$$$")
                movie.isFavourite = checkIfFavourite(viewModel.stateFavourite,movie.imdbID)

                MovieItem(
                    movie = movie,
                    onCardClick = {
                        navController.navigate(MovieScreen.Detail.screen_route + "/${movie.imdbID}")
                        Log.d("TAG", "$movie")
                    },
                    onFavouriteClicked = {
                        val favourite = FavouriteEntity(
                            imdbID = movie.imdbID,
                            favourite = true
                        )
                        if (movie.isFavourite==true){
                            viewModel.deleteFavourite(favourite)
                            movie.isFavourite = false
                        }else{
                            viewModel.addFavourite(favourite)
                            movie.isFavourite = true
                        }

                    }
                )
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.movies.isEmpty()) {
            Text(
                text = "OOPS NOTHING IS HERE.\n SET THE SEARCH PARAMETERS",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

    }

}

fun checkIfFavourite(favourites: List<FavouriteEntity>, imdbID: String): Boolean {
    var state = false
    for (i in favourites) {
        if (i.imdbID == imdbID) {
            state = true
        }
    }
    return state
}
