package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.data.RemoteConnection

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