package com.example.moviedbexampletest

import com.example.moviedbexampletest.models.GetMoviesResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val moviesApi: MoviesApi) {

    suspend fun getRecommendedMovies(apiKey : String) : Response<GetMoviesResponse>{
        return moviesApi.getRecommendedMovies(414906,apiKey,1)
    }

    suspend fun getDanishMovies(apiKey: String) : Response<GetMoviesResponse>{
        //return moviesApi.getTeluguMovies(apiKey)
        return moviesApi.getMoviesWithLanguage(apiKey,"da","US")
    }

    suspend fun getTeluguMovies(apiKey: String) : Response<GetMoviesResponse> {
        //return moviesApi.getTeluguMovies(apiKey)
        return moviesApi.getMoviesWithLanguage(apiKey,"hi-IN","IN")
    }


    suspend fun getNepaliMovies(apiKey: String) : Response<GetMoviesResponse> {
        //return moviesApi.getTeluguMovies(apiKey)
        return moviesApi.getMoviesWithLanguage(apiKey,"ne","US")
    }
}