package ru.dmitryskor.movies_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Retrofit
import ru.dmitryskor.movies_test.data.MoviesResponse
import ru.dmitryskor.movies_test.databinding.ActivityMainBinding
import ru.dmitryskor.movies_test.network.Common
import ru.dmitryskor.movies_test.network.RetrofitClient
import ru.dmitryskor.movies_test.network.RetrofitService
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllMovieList()
    }

    private fun getAllMovieList() {
        Common.retrofitService.getMovies().enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                call
                response
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                t
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}