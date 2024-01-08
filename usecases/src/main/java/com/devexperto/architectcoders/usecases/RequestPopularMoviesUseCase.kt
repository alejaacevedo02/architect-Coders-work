package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.data.MoviesRepository

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? =
        moviesRepository.requestPopularMovies()

}