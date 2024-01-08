package com.devexperto.architectcoders.data.datasource

import arrow.core.Either
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.domain.Error

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}