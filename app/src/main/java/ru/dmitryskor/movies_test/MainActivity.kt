package ru.dmitryskor.movies_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.dmitryskor.movies_test.databinding.ActivityMainBinding
import ru.dmitryskor.movies_test.network.MoviesClient

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        stateLoading()
        getAllMovieList()
    }

    private fun getAllMovieList() {
        lifecycleScope.launch {
            MoviesClient.getMovies({
                it
            }, {
                setStateErrorText(it.message)
            })
        }
    }

    private fun stateLoading() {
        binding.errorText.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun setStateErrorText(errorText: String?) {
        binding.progressBar.isVisible = false
        binding.errorText.isVisible = true
        binding.errorText.text = errorText
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}