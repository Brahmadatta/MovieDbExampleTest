package com.example.moviedbexampletest

import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val moviesApi: MoviesApi) {

    suspend fun getMovies(apiKey : String) : Response<GetMoviesResponse>{
        return moviesApi.getPopularMovies(apiKey,10)
    }
}