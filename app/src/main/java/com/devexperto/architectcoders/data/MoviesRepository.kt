package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.data.database.Movie
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource

class MoviesRepository(application: App) {
    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        application.getString(R.string.api_key)
    )

    val popularMovies = localDataSource.movies
    fun findById(id: Int) = localDataSource.getById(id)

    suspend fun requestPopularMovies() {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies.results.map { it.toLocalModel() })
        }
    }

    suspend fun switchFavorite(movie: Movie) {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        localDataSource.save(listOf(updatedMovie))
    }

}
