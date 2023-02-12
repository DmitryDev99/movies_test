package ru.dmitryskor.movies_test.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import ru.dmitryskor.movies_test.R
import ru.dmitryskor.movies_test.data.MovieUI
import ru.dmitryskor.movies_test.databinding.ActivityMainBinding
import ru.dmitryskor.movies_test.viewmodel.MoviesUiState
import ru.dmitryskor.movies_test.viewmodel.MoviesViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val adapterMovies by lazy(LazyThreadSafetyMode.NONE) {
        MoviesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spanColumn = 3
        adapterMovies.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Error -> errorState(this.getString(R.string.error_default))
                is LoadState.Loading -> loadingState()
                else -> idleState()
            }
        }
        binding.moviesRecyclerView.adapter =
            adapterMovies.withLoadStateFooter(MoviesLoaderAdapter())
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(this, spanColumn).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapterMovies.getItemViewType(position) != MoviesAdapter.LAST_ITEM) 1 else spanColumn
                }
            }
        }
        val viewModel: MoviesViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is MoviesUiState.Idle -> idleState()
                        is MoviesUiState.LoadMovies -> loadMovies(it.list)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.text.isVisible = false
        binding.progressBar.isVisible = true
        binding.moviesRecyclerView.isVisible = false
    }

    private fun idleState() {
        binding.progressBar.isVisible = false
        binding.text.isVisible = false
        binding.moviesRecyclerView.isVisible = true
    }

    private fun errorState(errorText: String?) {
        binding.progressBar.isVisible = false
        binding.moviesRecyclerView.isVisible = false
        binding.text.isVisible = true
        binding.text.text = errorText
    }

    private fun loadMovies(list: PagingData<MovieUI>) {
        binding.progressBar.isVisible = false
        binding.text.isVisible = false
        binding.moviesRecyclerView.isVisible = true
        adapterMovies.submitData(lifecycle, list)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}