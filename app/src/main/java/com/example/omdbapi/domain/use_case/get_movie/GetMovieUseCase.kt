package com.example.omdbapi.domain.use_case.get_movie

import com.example.omdbapi.common.Resource
import com.example.omdbapi.data.remote.model.MovieDto
import com.example.omdbapi.domain.repository.OmdbRepository
import com.example.omdbapi.utils.ApiLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: OmdbRepository
) {
    private val TAG = "GetMovieUseCase"
    operator fun invoke(id:String,apiKey:String) : Flow<Resource<MovieDto>> = flow{
        try {
            emit(Resource.Loading<MovieDto>())
            val movie = repository.getMovie(id,apiKey)
            emit(Resource.Success<MovieDto>(movie))
            ApiLogger.isSuccess(TAG,movie)
        }catch (e: HttpException){
            emit(Resource.Error<MovieDto>(e.localizedMessage?:"An unexpected error occurred."))
            ApiLogger.isUnSuccess(TAG, e.localizedMessage?:"An unexpected error occurred.")
        }catch (e: IOException){
            emit(Resource.Error<MovieDto>("Could not reach the server. Check internet connection."))
            ApiLogger.isFailure(TAG,"${e.message}")
        }
    }
}