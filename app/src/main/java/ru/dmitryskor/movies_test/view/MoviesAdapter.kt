package ru.dmitryskor.movies_test.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.dmitryskor.movies_test.data.Movie
import ru.dmitryskor.movies_test.databinding.MovieItemBinding

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
class MoviesAdapter : PagingDataAdapter<Movie, MovieVH>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        return MovieVH(parent)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(getItem(position), position)
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.displayTitle == newItem.displayTitle
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

class MovieVH(
    parent: ViewGroup,
    private val binding: MovieItemBinding = MovieItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie?, position: Int) {
        binding.nameMovie.text = position.toString() + "    " + movie?.displayTitle
    }
}