package ru.dmitryskor.movies_test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dmitryskor.movies_test.data.MovieUI
import ru.dmitryskor.movies_test.network.MoviesClient
import ru.dmitryskor.movies_test.repository.FilterMovies
import ru.dmitryskor.movies_test.repository.MoviesDSImpl

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 * модель для экрана списка фильмов
 */

sealed class MoviesUiState {
    object Idle: MoviesUiState()
    data class LoadMovies(val list: PagingData<MovieUI>): MoviesUiState()
}

class MoviesViewModel(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope) {

    init {
        coroutineScope.launch {
            getMovies()
        }
    }



    private val userFilter = MutableStateFlow(FilterMovies.ALL)

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Idle)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getMovies() {
        userFilter.flatMapLatest {
                filter ->
            MoviesDSImpl().getMovies(MoviesClient, filter).mapLatest { paging ->
                paging.map {
                    MovieUI(
                        it.displayTitle,
                        it.mpaaRating,
                        it.criticsPick == 1,
                        it.multimedia?.src
                    )
                }
            }.cachedIn(coroutineScope)
        }.collectLatest {
            _uiState.emit(MoviesUiState.LoadMovies(it))
        }
    }

    fun changeUserFilter(filterMovies: FilterMovies) {
        coroutineScope.launch {
            userFilter.emit(filterMovies)
        }
    }

}