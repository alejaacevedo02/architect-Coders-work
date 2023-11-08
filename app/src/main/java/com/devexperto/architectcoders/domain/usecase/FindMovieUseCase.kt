package com.devexperto.architectcoders.domain.usecase

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.database.Movie
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<Movie> =
        moviesRepository.findById(id)
}