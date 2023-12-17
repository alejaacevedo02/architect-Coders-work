package com.devexperto.architectcoders.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id == :id")
     fun findById(id: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movie: List<Movie>)

    @Query("SELECT COUNT(id) FROM Movie")
    suspend fun movieCount(): Int

    @Update
    suspend fun updateMovie(movie: Movie)
}