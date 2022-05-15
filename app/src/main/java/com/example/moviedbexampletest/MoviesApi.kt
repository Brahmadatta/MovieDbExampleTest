package com.example.moviedbexampletest

import com.example.moviedbexampletest.models.GetMoviesResponse
import com.example.moviedbexampletest.util.MovieConstants
import retrofit2.Response
import retrofit2.http.*

interface MoviesApi {

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") apiKey: String = MovieConstants.API_KEY,
        @Query("page") page: Int
    ): Response<GetMoviesResponse>

    @GET("discover/movie")
    suspend fun getMoviesWithLanguage(
        @Query("api_key") apiKey: String = MovieConstants.API_KEY,
        @Query("language") language : String,
        @Query("region") region : String,
    ): Response<GetMoviesResponse>

}