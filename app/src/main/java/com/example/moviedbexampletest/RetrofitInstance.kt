package com.example.moviedbexampletest

import com.example.moviedbexampletest.util.MovieConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api : MoviesApi by lazy {
        Retrofit.Builder()
            .baseUrl(MovieConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }


}