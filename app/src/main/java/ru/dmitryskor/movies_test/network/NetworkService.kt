package ru.dmitryskor.movies_test.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dmitryskor.movies_test.BuildConfig

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
object NetworkService {

    private const val INCORRECT_API_KEY = 401

    suspend fun asyncGet(url: String, methodName: String, params: Map<String, String>): JsonObject {
        return RetrofitClient.getClient(url).create(NetworkApi::class.java)
            .get(methodName, BuildConfig.MOVIES_API_KEY, params)
    }


    fun asyncGet(url: String, methodName: String, params: Map<String, String>, onSuccess: (JsonObject?) -> Unit, onError: (Throwable) -> Unit) {
        RetrofitClient.getClient(url).create(NetworkApi::class.java)
            .getCall(methodName, BuildConfig.MOVIES_API_KEY, params).enqueue(getCallBackResponse(onSuccess, onError))
    }

    private fun <T> getCallBackResponse(onSuccess: (T?) -> Unit, onError: (Throwable) -> Unit): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess.invoke(response.body())
                } else if (response.code() == INCORRECT_API_KEY) {
                    onError.invoke(Exception("code: ${response.code()}. Error msg: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onError.invoke(t)
            }
        }
    }
}