package com.example.moviedbexampletest

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = MovieConstants.API_KEY,
        @Query("page") page: Int
    ): Response<GetMoviesResponse>


}