package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.data.MoviesRepository
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? =
        moviesRepository.requestPopularMovies()

}