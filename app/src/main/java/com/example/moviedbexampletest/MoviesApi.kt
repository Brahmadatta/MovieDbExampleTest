package com.example.moviedbexampletest

import retrofit2.Response
import retrofit2.http.*

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = MovieConstants.API_KEY,
        @Query("page") page: Int
    ): Response<GetMoviesResponse>

    @GET("discover/movie")
    suspend fun getMoviesWithLanguage(
        @Query("api_key") apiKey: String = MovieConstants.API_KEY,
        @Query("language") language : String,
        @Query("region") region : String
    ): Response<GetMoviesResponse>

}