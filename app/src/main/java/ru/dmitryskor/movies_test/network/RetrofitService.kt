package ru.dmitryskor.movies_test.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
interface RetrofitService {
    @GET("{method}")
    fun getCall(
        @Path(value = "method", encoded = true) method: String,
        @Query("api-key") apiKey: String
    ): Call<JsonObject?>
}