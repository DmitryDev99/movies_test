package ru.dmitryskor.movies_test.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dmitryskor.movies_test.data.Movie
import ru.dmitryskor.movies_test.databinding.MovieItemBinding

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
class MoviesAdapter : RecyclerView.Adapter<MovieVH>() {

    private val listMovie = mutableListOf<Movie?>()

    fun setData(newList: List<Movie?>) {
        listMovie.clear()
        listMovie.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        return MovieVH(parent)
    }

    override fun getItemCount() = listMovie.size

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(listMovie[position])
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
    fun bind(movie: Movie?) {
        binding.nameMovie.text = movie?.displayTitle
    }
}