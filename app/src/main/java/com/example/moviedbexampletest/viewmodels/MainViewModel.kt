package com.example.moviedbexampletest.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbexampletest.models.GetMoviesResponse
import com.example.moviedbexampletest.models.Movie
import com.example.moviedbexampletest.Repository
import com.example.moviedbexampletest.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository,application: Application) : AndroidViewModel(application) {

    val recommendedMoviesResponse : MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    val popularTeluguMoviesResponse : MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    val popularDanishMoviesResponse : MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    val popularNepaliMoviesResponse : MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()

    fun getRecommendedMovies(apiKey : String) = viewModelScope.launch {
        getRecommendedMoviesData(apiKey)
    }

    fun getMoviesWithLanguageTelugu(apiKey: String) = viewModelScope.launch {
        getTeluguMoviesData(apiKey)
    }

    fun getMoviesWithLanguageDanish(apiKey: String) = viewModelScope.launch {
        getDanishMoviesData(apiKey)
    }
    fun getMoviesWithLanguageNepali(apiKey: String) = viewModelScope.launch {
            getNepaliMoviesData(apiKey)
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

    private suspend fun getNepaliMoviesData(apiKey: String) {
        popularNepaliMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remote.getNepaliMovies(apiKey)
                popularNepaliMoviesResponse.value = handleNepaliMoviesResponse(response)
                Log.e("loggednepali",response.body()?.movies.toString())
            }catch (e : Exception){
                Log.e("logged",e.message.toString())
                popularNepaliMoviesResponse.value = NetworkResult.Error("movies not found.")
            }
        }
    }

    private suspend fun getRecommendedMoviesData(apiKey: String) {
        recommendedMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecommendedMovies(apiKey)
                recommendedMoviesResponse.value = handlePopularMoviesResponse(response)
                Log.e("recommendedmovies",response.body()?.movies.toString())
                //Log.e("TAG", "getPopularMoviesData: "+response )
                //popularMoviesResponse.value = handlePopularMoviesResponse(response)
            }catch (e : Exception) {
                Log.e("logged",e.message.toString())
                recommendedMoviesResponse.value = NetworkResult.Error("movies not found.")
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

    private fun handleNepaliMoviesResponse(response: Response<GetMoviesResponse>): NetworkResult<List<Movie>>? {
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
