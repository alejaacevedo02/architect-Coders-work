package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.datasource.MovieRoomLocalDataSource
import com.devexperto.architectcoders.framework.datasource.MovieServerDataSource

class MoviesRepository(application: App) {
    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieRoomLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieServerDataSource(
        application.getString(R.string.api_key)
    )

    val popularMovies = localDataSource.movies
    fun findById(id: Int) = localDataSource.getById(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies)
        }
    }

    suspend fun switchFavorite(movie: Movie): Error? = tryCall{
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        localDataSource.save(listOf(updatedMovie))
    }

}
