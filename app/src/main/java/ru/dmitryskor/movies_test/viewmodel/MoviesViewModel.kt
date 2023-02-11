package ru.dmitryskor.movies_test.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dmitryskor.movies_test.network.MoviesClient

/**
 * Created by Dmitry Skorodumov on 11.02.2023
 * модель для экрана списка фильмов
 */

sealed class MoviesUiState {
    object Empty: MoviesUiState()
    object LoadingState: MoviesUiState()
    data class ErrorState(val errorText: String?): MoviesUiState()
}

class MoviesViewModel(
    private val coroutineScope: CloseableCoroutineScope = CloseableCoroutineScope()
) : ViewModel(coroutineScope) {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Empty)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()


    init {
        coroutineScope.launch {
            _uiState.update {
                MoviesUiState.LoadingState
            }
            MoviesClient.getMovies({
                it
            }, { throwable ->
                _uiState.update {
                    MoviesUiState.ErrorState(throwable.message)
                }
            })
        }
    }
}