package ru.dmitryskor.movies_test.network

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
object Common {
    private const val BASE_URL = "https://api.nytimes.com/svc/movies/v2/"
    val retrofitService: RetrofitService
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}