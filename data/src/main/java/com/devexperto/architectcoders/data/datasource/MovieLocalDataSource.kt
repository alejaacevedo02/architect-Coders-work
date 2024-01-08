package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.domain.Error
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun save(movies: List<Movie>) : Error?
    fun findById(id: Int): Flow<Movie>

    suspend fun isEmpty(): Boolean
}
