package com.example.omdbapi.presentation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.omdbapi.R

sealed class MovieScreen(val screen_route: String) {
    object Main : MovieScreen("main_screen")
    object Detail : MovieScreen("detail_screen")
}

@Composable
fun OmdbApiAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back_button"
                    )
                }
            }
        }
    )
}


@Composable
fun OmdbApiApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onIconSearchClick: () -> Unit,
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            CenterTopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = getTitleByRoute(context, MovieScreen.Main.screen_route),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dark_blue)
                    )
                },
                backgroundColor = Color.White,
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back_button"
                            )
                        }
                    }
                    IconButton(onClick ={ onIconSearchClick() }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "search_icon")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navController = navController)
        }
    }
}

fun getTitleByRoute(context: Context, route: String): String {
    return when (route) {
        "main_screen" -> context.getString(R.string.movie_main)
        "detail_screen" -> context.getString(R.string.movie_detail)
        else -> context.getString(R.string.app_name)
    }
}