package com.devexperto.architectcoders.model.datasource

import com.devexperto.architectcoders.model.RegionRepository
import com.devexperto.architectcoders.model.RemoteConnection

class MovieRemoteDataSource(
    private val apiKey: String,
) {
// As we depend on the model of the server we need to do a refactoring.

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                region
            )
}