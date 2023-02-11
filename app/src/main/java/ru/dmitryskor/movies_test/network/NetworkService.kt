package ru.dmitryskor.movies_test.network

import com.google.gson.JsonObject
import ru.dmitryskor.movies_test.BuildConfig

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
object NetworkService {

    suspend fun asyncGet(url: String, methodName: String, params: Map<String, String>): JsonObject {
        return RetrofitClient.getClient(url).create(NetworkApi::class.java)
            .get(methodName, BuildConfig.MOVIES_API_KEY, params)
    }

}