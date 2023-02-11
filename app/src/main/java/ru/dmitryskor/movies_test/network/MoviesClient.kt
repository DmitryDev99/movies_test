package ru.dmitryskor.movies_test.network

import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import ru.dmitryskor.movies_test.data.MoviesResponse
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */
object MoviesClient {

    private const val BASE_URL = "https://api.nytimes.com/svc/movies/v2/"

    suspend fun getMovies(onResponse: (MoviesResponse) -> Unit, onFailure: (Throwable) -> Unit) {
        try {
            val json = withContext(Dispatchers.IO) { getMoviesRequest() }
            val response = Gson().fromJson(json, MoviesResponse::class.java)
            onResponse(response)
        }
        catch (t: Throwable) {
            onFailure(t)
        }
    }

    private suspend fun getMoviesRequest(): JsonObject? =
        suspendCancellableCoroutine { cancellableContinuation ->
            NetworkService.asyncGet(BASE_URL, "reviews/all.json", { response ->
                cancellableContinuation.resume(response)
            }, { throwable ->
                if (cancellableContinuation.isActive) {
                    cancellableContinuation.resumeWithException(throwable)
                }
            })
        }
}