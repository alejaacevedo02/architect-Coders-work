package com.devexperto.architectcoders.model.datasource

import com.devexperto.architectcoders.model.RegionRepository
import com.devexperto.architectcoders.model.RemoteConnection

class MovieRemoteDataSource(
    private val apiKey: String,
    private val regionRepository: RegionRepository,
) {
// As we depend on the model of the server we need to do a refactoring.

    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                regionRepository.findLastRegion()
            )
}