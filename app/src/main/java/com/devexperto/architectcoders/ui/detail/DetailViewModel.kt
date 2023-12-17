package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.data.Error
import com.devexperto.architectcoders.data.toError
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.domain.usecases.FindMovieUseCase
import com.devexperto.architectcoders.domain.usecases.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    movieId: Int,
    private val findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect {
                    _state.value = UiState(it)
                }
        }
    }

    data class UiState(
        val movie: Movie? = null,
        val error: Error? = null
    )

    fun onFavoriteCLicked() {
        viewModelScope.launch {
            _state.value.movie?.let { movie ->
                val error = switchMovieFavoriteUseCase(movie)
                _state.update { it.copy(error = error) }
            }
        }
    }
}

    @Suppress("UNCHECKED_CAST")
    class DetailViewModelFactory(
        private val movieId: Int,
        private val findMovieUseCase: FindMovieUseCase,
        private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
    ) :
        ViewModelProvider.
        Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(movieId, findMovieUseCase, switchMovieFavoriteUseCase) as T
        }
    }
