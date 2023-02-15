package ru.dmitryskor.movies_test.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.dmitryskor.movies_test.data.Movie
import ru.dmitryskor.movies_test.network.MoviesClient

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 */

const val NETWORK_PAGE_SIZE = 20

class MoviesDSImpl {

    fun getMovies(moviesClient: MoviesClient, filterMovies: FilterMovies): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { MoviesPagingSource(moviesClient, filterMovies) }
        ).flow
    }

}