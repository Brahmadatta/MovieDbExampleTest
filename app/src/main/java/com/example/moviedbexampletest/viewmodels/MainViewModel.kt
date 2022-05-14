package com.example.moviedbexampletest.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbexampletest.GetMoviesResponse
import com.example.moviedbexampletest.Movie
import com.example.moviedbexampletest.Repository
import com.example.moviedbexampletest.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository,application: Application) : AndroidViewModel(application) {

    val popularMoviesResponse : MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    val popularTeluguMoviesResponse : MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    val popularDanishMoviesResponse : MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()

    fun getPopularMovies(apiKey : String) = viewModelScope.launch {
        getPopularMoviesData(apiKey)
    }

    fun getMoviesWithLanguageTelugu(apiKey: String) = viewModelScope.launch {
        getTeluguMoviesData(apiKey)
    }

    fun getMoviesWithLanguageDanish(apiKey: String) = viewModelScope.launch {
        getDanishMoviesData(apiKey)
    }

    private suspend fun getTeluguMoviesData(apiKey: String) {
        popularTeluguMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remote.getTeluguMovies(apiKey)
                popularTeluguMoviesResponse.value = handleTeluguMoviesResponse(response)
                Log.e("loggedtelugu",response.body()?.movies.toString())
            }catch (e : Exception){
                Log.e("logged",e.message.toString())
                popularTeluguMoviesResponse.value = NetworkResult.Error("movies not found.")
            }
        }
    }

    private suspend fun getDanishMoviesData(apiKey: String) {
        popularDanishMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remote.getDanishMovies(apiKey)
                popularDanishMoviesResponse.value = handleDanishMoviesResponse(response)
                Log.e("loggedanish",response.body()?.movies.toString())
            }catch (e : Exception){
                Log.e("logged",e.message.toString())
                popularDanishMoviesResponse.value = NetworkResult.Error("movies not found.")
            }
        }
    }

    private suspend fun getPopularMoviesData(apiKey: String) {
        popularMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getMovies(apiKey)
                popularMoviesResponse.value = handlePopularMoviesResponse(response)
                //Log.e("TAG", "getPopularMoviesData: "+response )
                //popularMoviesResponse.value = handlePopularMoviesResponse(response)
            }catch (e : Exception) {
                Log.e("logged",e.message.toString())
                popularMoviesResponse.value = NetworkResult.Error("movies not found.")
            }
        }
    }

    private fun handlePopularMoviesResponse(response: Response<GetMoviesResponse>): NetworkResult<List<Movie>>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            response.isSuccessful -> {
                val movieData = response.body()
                return NetworkResult.Success(movieData?.movies!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleTeluguMoviesResponse(response: Response<GetMoviesResponse>): NetworkResult<List<Movie>>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            response.isSuccessful -> {
                val movieData = response.body()
                return NetworkResult.Success(movieData?.movies!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleDanishMoviesResponse(response: Response<GetMoviesResponse>): NetworkResult<List<Movie>>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            response.isSuccessful -> {
                val movieData = response.body()
                return NetworkResult.Success(movieData?.movies!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    private fun hasInternetConnection() : Boolean {

        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false

        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            else -> false
        }

    }
}
