package com.example.omdbapi.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.omdbapi.presentation.movie_detail.MovieDetail
import com.example.omdbapi.presentation.movie_list.MovieList

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MovieScreen.Main.screen_route
    ) {
        composable(route = MovieScreen.Main.screen_route) {
            MovieList(navController)
        }
        composable(
            route = MovieScreen.Detail.screen_route + "/{imdbID}",
            arguments = listOf(navArgument("imdbID") { type = NavType.StringType })
        ) { backStackEntry ->
            MovieDetail()

        }
    }
}