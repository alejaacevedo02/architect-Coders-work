package com.devexperto.architectcoders.model

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.model.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(application: App) {

    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        application.getString(R.string.api_key), RegionRepository(application)
    )

    val popularMovies = localDataSource.movies
    fun findById(id: Int) = localDataSource.getById(id)

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies()
            localDataSource.save(movies.results.map { it.toLocalModel() })
        }
    }

}
