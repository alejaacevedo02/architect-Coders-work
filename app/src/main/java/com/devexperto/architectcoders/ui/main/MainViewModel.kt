package com.devexperto.architectcoders.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.model.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {


    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState>
        get() {
            if (state.value?.movies == null) {
                refresh()
            }
            return _state
        }

    private fun refresh() {
        scope.launch {
            _state.value = _state.value?.copy(loading = true)
            //Loading will be false after fetching movies
            _state.value = UiState(
                movies = moviesRepository.findPopularMovies().results
            )
        }
    }

    fun onMovieClicked(movie: Movie) {
        _state.value = _state.value?.copy(
            navigateTo = movie
        )
    }
}

data class UiState(
    val loading: Boolean = false,
    val movies: List<Movie>? = null,
    val navigateTo: Movie? = null
)