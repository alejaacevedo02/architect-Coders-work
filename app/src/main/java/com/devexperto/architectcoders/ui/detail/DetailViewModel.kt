package com.devexperto.architectcoders.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.main.MainViewModel

class DetailViewModel(movie: Movie) : ViewModel() {

    private val _state = MutableLiveData(UiState(movie))
    val state: LiveData<UiState>
        get() = _state

}

data class UiState(val movie: Movie? = null)

class DetailViewModelFactory(private val movie: Movie) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movie) as T
    }
}