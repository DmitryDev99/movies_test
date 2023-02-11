package ru.dmitryskor.movies_test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dmitryskor.movies_test.data.Movie
import ru.dmitryskor.movies_test.network.MoviesClient
import ru.dmitryskor.movies_test.repository.MoviesDSImpl

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 * модель для экрана списка фильмов
 */

sealed class MoviesUiState {
    object Empty: MoviesUiState()
    object LoadingState: MoviesUiState()
    data class ErrorState(val errorText: String?): MoviesUiState()
    data class LoadMovies(val list: List<Movie?>): MoviesUiState()
}

class MoviesViewModel(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope) {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Empty)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        getMovies()
    }

    fun getMovies(): Flow<PagingData<Movie>> {
        return MoviesDSImpl().getMovies(MoviesClient)
            .map { pagingData ->
                pagingData.map {
                    it
                }
            }.cachedIn(coroutineScope)
    }
}