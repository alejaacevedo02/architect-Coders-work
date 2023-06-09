package com.devexperto.architectcoders.ui.detail

import androidx.databinding.BindingAdapter
import com.devexperto.architectcoders.model.database.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetail(movie: Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}