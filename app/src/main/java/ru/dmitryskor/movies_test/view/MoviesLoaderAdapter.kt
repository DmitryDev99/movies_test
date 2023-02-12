package ru.dmitryskor.movies_test.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dmitryskor.movies_test.databinding.MovieErrorItemBinding
import ru.dmitryskor.movies_test.databinding.MovieProgressItemBinding

/**
 * Created by Dmitry Skorodumov on 12.02.2023
 */
class MoviesLoaderAdapter : LoadStateAdapter<MoviesLoaderAdapter.ItemVH>() {

    override fun onBindViewHolder(holder: ItemVH, loadState: LoadState) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemVH {
        return when (loadState) {
            LoadState.Loading -> LoadingVH(parent)
            is LoadState.Error -> ErrorVH(parent)
            is LoadState.NotLoading -> error("error state")
        }
    }

    abstract class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind()
    }

    class LoadingVH(
        parent: ViewGroup,
        binding: MovieProgressItemBinding = MovieProgressItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : ItemVH(binding.root) {
        override fun bind() {  }
    }

    class ErrorVH(
        parent: ViewGroup,
        binding: MovieErrorItemBinding = MovieErrorItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : ItemVH(binding.root) {
        override fun bind() {  }
    }
}
