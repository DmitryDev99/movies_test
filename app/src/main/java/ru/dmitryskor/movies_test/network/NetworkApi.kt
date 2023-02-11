package ru.dmitryskor.movies_test.network

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
interface NetworkApi {
    @GET("{method}")
    suspend fun get(
        @Path(value = "method", encoded = true) method: String,
        @Query("api-key") apiKey: String,
        @QueryMap params: Map<String, String>
    ): JsonObject
}
