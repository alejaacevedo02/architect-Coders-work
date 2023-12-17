package com.devexperto.architectcoders.framework.datasource

import com.devexperto.architectcoders.data.RemoteConnection
import com.devexperto.architectcoders.data.RemoteMovie
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Movie

class MovieServerDataSource(
    private val apiKey: String,
) : MovieRemoteDataSource {
// As we depend on the model of the server we need to do a refactoring.

    override suspend fun findPopularMovies(region: String): List<Movie> =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                region
            ).results.toDomainModel()
}

private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )