package com.example.omdbapi.presentation.movie_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdbapi.data.remote.model.Search
import com.skydoves.landscapist.glide.GlideImage
import com.example.omdbapi.R
import com.example.omdbapi.presentation.movie_list.MovieListViewModel

@Composable
fun MovieItem(
    movie: Search,
    onCardClick: () -> Unit,
    onFavouriteClicked: () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    /*var favourite by remember{
        mutableStateOf(false)
    }*/

    //Log.d("TAG,""Movie ITEMMMMM $movie")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp, top = 15.dp)
            .clickable { onCardClick() },
        elevation = 10.dp,

        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
        ) {

            GlideImage(
                imageModel = movie.Poster,
                modifier = Modifier
                    .weight(1.5f)
                    .width(100.dp)
                    .height(140.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopStart
            )
            Spacer(modifier = Modifier.weight(0.3f))

            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(10.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "${movie.Title}.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    color = colorResource(R.color.dark_blue)
                )
                Text(
                    text = movie.Year,
                    fontSize = 16.sp,
                    modifier = Modifier,
                    color = colorResource(R.color.dark_blue)

                )
                Row(
                    modifier = Modifier.padding(top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.Type,
                        fontSize = 14.sp,
                        modifier = Modifier

                    )
                    Spacer(modifier = Modifier.weight(2f))
                    IconButton(onClick = { }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_comment),
                            contentDescription = "Comment"
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.1f))


                    IconButton(
                        onClick = {
                            onFavouriteClicked()
                        }
                    ) {

                        Image(
                            painter = if (movie.isFavourite == true) painterResource(
                                id = R.drawable.ic_liked
                            ) else painterResource(
                                id = R.drawable.ic_like
                            ), contentDescription = ""
                        )

                    }


                }

            }


        }
    }
}



