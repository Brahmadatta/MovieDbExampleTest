package com.example.moviedbexampletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbexampletest.databinding.ActivityMainBinding
import com.example.moviedbexampletest.util.NetworkResult
import com.example.moviedbexampletest.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding : ActivityMainBinding ?= null

    private val binding get() = _binding!!
    private val mAdapter by lazy { MoviesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.mainviewmodel = mainViewModel
        setContentView(_binding!!.root)

        setUpRecyclerView()

        mainViewModel.getPopularMovies(MovieConstants.API_KEY)
        mainViewModel.getTeluguMovies(MovieConstants.API_KEY)
        getApiData()
    }

    fun setUpRecyclerView(){
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }

    private fun getApiData() {

        mainViewModel.popularMoviesResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}