package com.devexperto.architectcoders.framework.database

import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomLocalDataSource(private val movieDao: MovieDao) : MovieLocalDataSource {
    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>) {
        movieDao.addMovies(movies.fromDomainModel())
    }

    override fun getById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0
}

private fun List<com.devexperto.architectcoders.framework.database.Movie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }
private fun com.devexperto.architectcoders.framework.database.Movie.toDomainModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

private fun List<Movie>.fromDomainModel(): List<com.devexperto.architectcoders.framework.database.Movie> = map { it.fromDomainModel() }

private fun Movie.fromDomainModel(): com.devexperto.architectcoders.framework.database.Movie =
    com.devexperto.architectcoders.framework.database.Movie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )