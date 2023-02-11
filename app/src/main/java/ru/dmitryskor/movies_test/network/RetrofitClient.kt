package ru.dmitryskor.movies_test.network

import retrofit2.Retrofit

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonCon)
                .baseUrl(baseUrl)
                .build()
        }
        return retrofit!!
    }

}