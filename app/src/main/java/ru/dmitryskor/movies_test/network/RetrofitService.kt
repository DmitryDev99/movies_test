package ru.dmitryskor.movies_test.network

import retrofit2.Call
import retrofit2.http.GET
import ru.dmitryskor.movies_test.BuildConfig
import ru.dmitryskor.movies_test.data.MoviesResponse

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
interface RetrofitService {
    @GET("reviews/all.json?api-key=" + BuildConfig.MOVIES_API_KEY)
    fun getMovies(): Call<MoviesResponse>
}