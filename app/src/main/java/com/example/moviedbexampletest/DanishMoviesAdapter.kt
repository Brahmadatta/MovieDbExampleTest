package com.example.moviedbexampletest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbexampletest.DanishMoviesAdapter.ViewHolder.Companion.VIEW_TYPE_HINDI
import com.example.moviedbexampletest.databinding.MoviesRowLayoutBinding

class DanishMoviesAdapter (private val onClick: (String) -> Unit): RecyclerView.Adapter<DanishMoviesAdapter.ViewHolder>() {

    private var movie = emptyList<Movie>()

    class ViewHolder(private val binding : MoviesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie){
            binding.movie = movie
            binding.executePendingBindings()
        }

        companion object {
            const val VIEW_TYPE_HINDI = 1111
            fun from(parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MoviesRowLayoutBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentResult = movie[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_HINDI
    }

    fun setData(newData : List<Movie>){
        val movieDiffUtil = MovieDiffUtil(movie,newData)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        movie = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}