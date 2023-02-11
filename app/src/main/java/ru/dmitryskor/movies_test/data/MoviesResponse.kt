package ru.dmitryskor.movies_test.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 * response to call getMovies
 */
data class MoviesResponse(
    val status: String?,
    val copyright: String?,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("num_results")
    val numResults: Int,
)
