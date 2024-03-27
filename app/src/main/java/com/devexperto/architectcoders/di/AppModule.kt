package com.devexperto.architectcoders.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.data.AndroidPermissionChecker
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.PlayServicesLocationDataSource
import com.devexperto.architectcoders.data.database.MovieDataBase
import com.devexperto.architectcoders.data.database.MovieRoomLocalDataSource
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.data.server.MovieServerDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)


    @Provides
    @Singleton
    fun provideDataBase(app: Application) = Room.databaseBuilder(
        app,
        MovieDataBase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun provideRemoteDataSource(@ApiKey apiKey: String): MovieRemoteDataSource =
        MovieServerDataSource(apiKey)

    @Provides
    fun provideLocalDataSource(db: MovieDataBase): MovieLocalDataSource =
        MovieRoomLocalDataSource(db.movieDao())

    @Provides
    fun provideLocationDataSource(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun providePermissionChecker(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}