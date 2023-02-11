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
    val results: List<Movie?>?
)

data class Movie(
    @SerializedName("display_title") val displayTitle: String?,
    @SerializedName("mpaa_rating") val mpaaRating: String?,
    @SerializedName("critics_pick") val criticsPick: Int?,
    val byline: String?,
    @SerializedName("headline") val headline: String?,
    @SerializedName("summary_short") val summaryShort: String?,
    @SerializedName("publication_date") val publicationDate: String?,
    @SerializedName("opening_date") val openingDate: String?,
    @SerializedName("date_updated") val dateUpdated: String?,
    @SerializedName("link") val link: Link?,
    @SerializedName("multimedia")
    val multimedia: Multimedia?,
)

data class Link(
    val type: String?,
    val url: String?,
    @SerializedName("suggested_link_text") val suggestedLinkText: String?,
)

data class Multimedia(
    val type: String?,
    val src: String?,
    val width: Int?,
    val height: Int?,
)
