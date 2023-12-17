package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.framework.database.MovieDao
import com.devexperto.architectcoders.framework.database.Movie as DbMovie
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun save(movies: List<Movie>)
    fun getById(id: Int): Flow<Movie>

    suspend fun isEmpty(): Boolean
}
