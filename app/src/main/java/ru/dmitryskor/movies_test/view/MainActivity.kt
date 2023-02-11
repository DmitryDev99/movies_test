package ru.dmitryskor.movies_test.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.dmitryskor.movies_test.databinding.ActivityMainBinding
import ru.dmitryskor.movies_test.viewmodel.MoviesUiState
import ru.dmitryskor.movies_test.viewmodel.MoviesViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: MoviesViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is MoviesUiState.Empty -> emptyState()
                        is MoviesUiState.LoadingState -> loadingState()
                        is MoviesUiState.ErrorState -> errorState(it.errorText)
                    }
                }
            }
        }
    }

    private fun emptyState() {
        binding.errorText.isVisible = false
        binding.progressBar.isVisible = false
    }
    private fun loadingState() {
        binding.errorText.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun errorState(errorText: String?) {
        binding.progressBar.isVisible = false
        binding.errorText.isVisible = true
        binding.errorText.text = errorText
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}