package ru.dmitryskor.movies_test.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.dmitryskor.movies_test.data.Movie
import ru.dmitryskor.movies_test.network.MoviesClient

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */

private const val STARTING_PAGE_INDEX = 0

class MoviesPagingSource(
    private val service: MoviesClient
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getMovies(pageIndex)
            LoadResult.Page(
                data = response.results ?: emptyList(),
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = if (response.hasMore) {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                } else {
                    null
                }
            )
        }
        catch (t: Throwable) {
            return LoadResult.Error(t)
        }
    }
}