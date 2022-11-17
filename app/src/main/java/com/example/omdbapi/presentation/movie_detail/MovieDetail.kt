package com.example.omdbapi.presentation.movie_detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdbapi.R
import com.example.omdbapi.data.local.model.CommentEntity
import com.example.omdbapi.data.local.model.FavouriteEntity
import com.example.omdbapi.data.remote.model.MovieDto
import com.example.omdbapi.presentation.movie_list.MovieListViewModel
import com.example.omdbapi.presentation.movie_list.checkIfFavourite
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun MovieDetail(
    viewModelDetail:MovieDetailViewModel = hiltViewModel(),
    viewModelList:MovieListViewModel = hiltViewModel()
){
    val state = viewModelDetail.stateApi.value
    var stateComments = viewModelDetail.stateComments

    LaunchedEffect(Unit) {
        viewModelList.getFavourites()

    }

    Box(modifier = Modifier.fillMaxSize()) {
        state.movie?.let { movie->
            state.movie.let { viewModelDetail.getComments(it.imdbID) }
            movie.isFavourite = checkIfFavourite(viewModelList.stateFavourite,movie.imdbID)
            LazyColumn(modifier = Modifier.fillMaxSize(),contentPadding = PaddingValues(20.dp)){
                item {
                    MovieCard(movie = movie ,
                        onFavouriteClicked = {
                            val favourite = FavouriteEntity(
                                imdbID = movie.imdbID,
                                favourite = true
                            )
                            if (movie.isFavourite==true){
                                viewModelList.deleteFavourite(favourite)
                            }else{
                                viewModelList.addFavourite(favourite)
                            }
                    },
                        imdbClicked = {

                    })
                    Text(
                        text = "COMMENTS",
                        fontSize = 14.sp,
                        color = colorResource(R.color.dark_blue),
                        modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 5.dp)
                    )
                    AddCommentCard(movie = movie, viewModel = viewModelDetail)
                }
                items(stateComments){ commentEntity->
                    Log.d("TAGComment","$commentEntity")
                    CommentCard(commentEntity = commentEntity, onDeleteClick = {
                        viewModelDetail.deleteComment(commentEntity)
                    })
                }
            }
        }
        if (state.error.isNotBlank())  {
            Text(text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
@Composable
fun MovieCard(
    movie:MovieDto,
    modifier: Modifier = Modifier,
    onFavouriteClicked: () -> Unit,
    imdbClicked:()->Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)) {
            GlideImage(
                imageModel =movie.Poster,
                modifier = Modifier
                    .weight(0.8f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)) {
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
                Spacer(modifier = Modifier.padding(top = 30.dp))
                Text(
                    text = movie.Type,
                    fontSize = 14.sp,
                    modifier = Modifier

                )
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxSize()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Spacer(modifier = Modifier.weight(2f))
                        IconButton(onClick = { imdbClicked() }) {
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

@Composable
fun AddCommentCard(viewModel: MovieDetailViewModel, movie: MovieDto) {
    var text by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .height(100.dp),
                value = text,
                onValueChange = { text = it },
                label = { Text("Write your comment") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(R.color.light_gray),
                    cursorColor = colorResource(R.color.light_gray),
                    disabledLabelColor = colorResource(R.color.light_gray),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                    ),
                shape = RoundedCornerShape(15)

            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            val localContext = LocalContext.current
            Button(
                onClick = {
                    if(text.isNotEmpty()){
                        viewModel.addComment(CommentEntity(imdbID = movie.imdbID, comment = text))
                    }else{
                        Toast.makeText(localContext,"Fill field !!!",Toast.LENGTH_SHORT).show()
                    }
                         },
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.dark_blue),
                    contentColor = Color.White),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxSize()
                    .height(70.dp)
                    .padding(bottom = 20.dp)
            ) {
                Text(text = "Leave comment")
            }
        }

    }

}

@Composable
fun CommentCard(commentEntity: CommentEntity,onDeleteClick:()->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(15.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize().fillMaxWidth().align(Alignment.CenterHorizontally)) {
                Text(
                    text = commentEntity.id.toString(),
                    fontSize = 16.sp,
                    color = colorResource(R.color.dark_blue),
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 5.dp)
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Text(
                    text = commentEntity.comment,
                    fontSize = 16.sp,
                    color = colorResource(R.color.dark_blue),
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 5.dp)
                )
                IconButton(onClick = { onDeleteClick()}, modifier = Modifier.align(Alignment.Bottom)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete_book",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
            Row(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = commentEntity.imdbID,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 5.dp)
                )
            }
        }

    }
}
