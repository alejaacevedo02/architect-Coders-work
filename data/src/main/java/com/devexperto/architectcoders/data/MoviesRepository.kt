package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {

    val popularMovies = localDataSource.movies
    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            movies.fold(
                ifLeft = { return it },
                ifRight = {
                    localDataSource.save(it)
                }
            )
        }
        return null
    }


    suspend fun switchFavorite(movie: Movie): Error? {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        return localDataSource.save(listOf(updatedMovie))
    }

}
