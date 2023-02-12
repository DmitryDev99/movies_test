package ru.dmitryskor.movies_test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dmitryskor.movies_test.data.MovieUI
import ru.dmitryskor.movies_test.network.MoviesClient
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
            MoviesDSImpl().getMovies(MoviesClient)
                .map { pagingData ->
                    pagingData.map {
                        MovieUI(
                            it.displayTitle,
                            it.mpaaRating,
                            it.criticsPick == 1,
                            it.multimedia?.src
                        )
                    }
                }.cachedIn(coroutineScope).collectLatest {
                    _uiState.emit(MoviesUiState.LoadMovies(it))
                }
        }
    }

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Idle)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()
}