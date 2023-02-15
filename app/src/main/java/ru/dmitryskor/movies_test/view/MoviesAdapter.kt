package ru.dmitryskor.movies_test.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.dmitryskor.movies_test.R
import ru.dmitryskor.movies_test.data.MovieUI
import ru.dmitryskor.movies_test.databinding.MovieItemBinding

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
class MoviesAdapter : PagingDataAdapter<MovieUI, MovieVH>(MovieDiffCallBack()) {

    companion object {
        const val ANY_ITEM = 0
        const val LAST_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount -> LAST_ITEM
            else -> ANY_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        return MovieVH(parent)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<MovieUI>() {
    override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem.nameMovie == newItem.nameMovie
    }

    override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
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
    fun bind(movie: MovieUI?) {
        binding.nameMovie.text = movie?.nameMovie
        if (!movie?.mpaaRating.isNullOrEmpty()) {
            binding.textRating.isVisible = true
            val (color, colorText) = if (movie?.criticsPick == true) {
                Pair(Color.GREEN, Color.BLACK)
            } else {
                Pair(Color.RED, Color.WHITE)
            }
            binding.textRating.setBackgroundColor(color)
            binding.textRating.setTextColor(colorText)
            binding.textRating.text = movie?.mpaaRating
        } else {
            binding.textRating.isVisible = false
        }
        val requestOption = RequestOptions().transform(CenterCrop(), RoundedCorners(12))
        Glide.with(binding.root.context)
            .load(movie?.imageUrl)
            .error(R.drawable.ic_movie_plug)
            .placeholder(R.drawable.ic_movie_plug)
            .apply(requestOption)
            .into(binding.imageMovie)
    }
}