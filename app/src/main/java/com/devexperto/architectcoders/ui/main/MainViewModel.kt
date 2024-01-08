package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.data.toError
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.update { UiState(movies = movies) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.update { it.copy(error = error) }
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val movies: List<Movie>? = null,
    val error: Error? = null
)

class MainViewModelFactory(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            requestPopularMoviesUseCase,
            getPopularMoviesUseCase
        ) as T
    }

}