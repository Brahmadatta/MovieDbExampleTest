package com.example.moviedbexampletest

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.moviedbexampletest.util.MovieConstants

class MoviesRowBinding {

    companion object {
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView,imageUrl : String){
            imageView.load(MovieConstants.IMAGE_BASE_URL + imageUrl) {
                crossfade(600)
            }
        }
    }
}