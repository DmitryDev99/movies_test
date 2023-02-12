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
        adapterMovies.addLoadStateListener { state ->
            if (state.refresh is LoadState.Error) {
                errorState(this.getString(R.string.error_default))
            }
        }
        binding.moviesRecyclerView.adapter = adapterMovies.withLoadStateHeaderAndFooter(
            MoviesLoaderAdapter(), MoviesLoaderAdapter()
        )
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(this, 3)
        val viewModel: MoviesViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is MoviesUiState.Empty -> emptyState()
                        is MoviesUiState.LoadingState -> loadingState()
                        is MoviesUiState.ErrorState -> errorState(it.errorText)
                        is MoviesUiState.LoadMovies -> loadMovies(it.list)
                    }
                }
            }
        }
    }

    private fun emptyState() {
        binding.text.isVisible = true
        binding.text.text = "List movies empty"
        binding.progressBar.isVisible = false
        binding.moviesRecyclerView.isVisible = false
    }
    private fun loadingState() {
        binding.text.isVisible = false
        binding.progressBar.isVisible = true
        binding.moviesRecyclerView.isVisible = false
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