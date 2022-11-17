package com.example.omdbapi.domain.use_case.get_movies

import com.example.omdbapi.common.Resource
import com.example.omdbapi.data.remote.model.MoviesDto
import com.example.omdbapi.domain.repository.OmdbRepository
import com.example.omdbapi.utils.ApiLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: OmdbRepository
) {
    private val TAG = "GetMoviesUseCase"
    operator fun invoke(name:String,page:Int,apiKey:String) : Flow<Resource<MoviesDto>> = flow{
        try {
            emit(Resource.Loading<MoviesDto>())
            val movies = repository.getMovies(name,page,apiKey)
            emit(Resource.Success<MoviesDto>(movies))
            ApiLogger.isSuccess(TAG,"$movies")
        }catch (e: HttpException){
            emit(Resource.Error<MoviesDto>(e.localizedMessage?:"An unexpected error occurred."))
            ApiLogger.isUnSuccess(TAG, e.localizedMessage?:"An unexpected error occurred.")
        }catch (e: IOException){
            emit(Resource.Error<MoviesDto>("Could not reach the server. Check internet connection."))
            ApiLogger.isFailure(TAG,"${e.message}")
        }
    }
}