package com.example.moviedbexampletest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedbexampletest.DanishMoviesAdapter.ViewHolder.Companion.VIEW_TYPE_HINDI
import com.example.moviedbexampletest.MoviesAdapter.ViewHolder.Companion.VIEW_TYPE
import com.example.moviedbexampletest.TeluguMoviesAdapter.ViewHolder.Companion.VIEW_TYPE_TELUGU
import com.example.moviedbexampletest.databinding.ActivityMainBinding
import com.example.moviedbexampletest.util.NetworkResult
import com.example.moviedbexampletest.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding : ActivityMainBinding?= null

    private val binding get() = _binding!!
    private val moviesAdapter by lazy { MoviesAdapter{
                onMoviesItemClicked(it)
    } }



    private val mTeluguAdapter by lazy { TeluguMoviesAdapter {
        onMoviesItemClicked(it)
    } }
    private val mDanishAdapter by lazy { DanishMoviesAdapter {
        onMoviesItemClicked(it)
    } }
    private val mKannadaAdapter by lazy { KannadaMoviesAdapter() }


    private val horizontalWrapperAdapter: HorizontalWrapperAdapter by lazy {
        HorizontalWrapperAdapter(moviesAdapter)
    }

    private val horizontalWrapperTeluguAdapter: HorizontalWrapperTeluguAdapter by lazy {
        HorizontalWrapperTeluguAdapter(mTeluguAdapter)
    }

    private val horizontalWrapperHindiAdapter: HorizontalWrapperHindiAdapter by lazy {
        HorizontalWrapperHindiAdapter(mDanishAdapter)
    }



    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        ConcatAdapter(config, horizontalWrapperAdapter,horizontalWrapperHindiAdapter,horizontalWrapperTeluguAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.mainviewmodel = mainViewModel
        setContentView(_binding!!.root)

        setUpRecyclerView()

        mainViewModel.getPopularMovies(MovieConstants.API_KEY)
        mainViewModel.getMoviesWithLanguageTelugu(MovieConstants.API_KEY)
        mainViewModel.getMoviesWithLanguageDanish(MovieConstants.API_KEY)
        getApiData()
    }
    private fun getApiData() {

        mainViewModel.popularMoviesResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { moviesAdapter.setData(it) }
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

        mainViewModel.popularTeluguMoviesResponse.observe(this){ response ->

            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { mTeluguAdapter.setData(it) }
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

        mainViewModel.popularDanishMoviesResponse.observe(this){ response ->

            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { mDanishAdapter.setData(it) }
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

    fun setUpRecyclerView(){
        val layoutManager = GridLayoutManager(this, 12)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    VIEW_TYPE -> 10

                    VIEW_TYPE_TELUGU -> 10

                    VIEW_TYPE_HINDI -> 10

                    else -> 12
                }
            }
        }

        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = concatAdapter
    }

    private fun onMoviesItemClicked(it: String) {
        Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
    }

}