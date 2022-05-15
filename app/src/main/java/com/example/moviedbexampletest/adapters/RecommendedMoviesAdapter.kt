package com.example.moviedbexampletest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbexampletest.models.Movie
import com.example.moviedbexampletest.MovieDiffUtil
import com.example.moviedbexampletest.adapters.RecommendedMoviesAdapter.ViewHolder.Companion.VIEW_TYPE
import com.example.moviedbexampletest.databinding.MoviesRowLayoutBinding

class RecommendedMoviesAdapter(private val onClick: (String) -> Unit
) : RecyclerView.Adapter<MoviesViewHolder>() {

    private var movie = emptyList<Movie>()

    class ViewHolder(private val binding : MoviesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

//        fun bind(movie: Movie){
//            binding.movie = movie
//            binding.executePendingBindings()
//        }

        companion object {

            const val VIEW_TYPE = 4444

//            fun from(parent: ViewGroup) : ViewHolder{
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = MoviesRowLayoutBinding.inflate(layoutInflater)
//                return ViewHolder(binding)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MoviesRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentResult = movie[position]
        holder.bind(currentResult,onClick)
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun setData(newData : List<Movie>){
        val movieDiffUtil = MovieDiffUtil(movie,newData)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        movie = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}

class MoviesViewHolder(
    private val binding: MoviesRowLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie, onClick: (String) -> Unit) {
        binding.movie = movie
        binding.root.setOnClickListener { onClick(movie.title) }
    }
}