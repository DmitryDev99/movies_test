package ru.dmitryskor.movies_test.network

import com.google.gson.Gson
import ru.dmitryskor.movies_test.data.MoviesResponse
import ru.dmitryskor.movies_test.repository.FilterMovies

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
object MoviesClient {

    private const val BASE_URL = "https://api.nytimes.com/svc/movies/v2/"

    suspend fun getMovies(pageIndex: Int, filterMovies: FilterMovies): MoviesResponse {
        val json = NetworkService.asyncGet(BASE_URL, "reviews/${filterMovies.name.lowercase()}.json",
            mapOf("offset" to pageIndex.toString()))
        return Gson().fromJson(json, MoviesResponse::class.java)
    }
}