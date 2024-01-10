package com.devexperto.architectcoders.di

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.data.AndroidPermissionChecker
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.PlayServicesLocationDataSource
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.data.database.MovieDataBase
import com.devexperto.architectcoders.data.database.MovieRoomLocalDataSource
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.data.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.data.server.MovieServerDataSource
import com.devexperto.architectcoders.ui.common.app
import com.devexperto.architectcoders.ui.detail.DetailViewModel
import com.devexperto.architectcoders.ui.main.MainViewModel
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDi() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDi)
        modules(listOf(appModule, dataModule, useCasesModule))
    }
}

private val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            MovieDataBase::class.java, "movie-db"
        ).build()
    }
    factory<MovieLocalDataSource> { MovieRoomLocalDataSource(get()) }
    factory<MovieRemoteDataSource> {
        MovieServerDataSource(get(named("apiKey")))

    }
    single { get<MovieDataBase>().movieDao() }
    single(named("apiKey")) { androidContext().getString(R.string.api_key) }

    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
}


private val dataModule = module {
    factory {
        RegionRepository(
            locationDataSource = get(),
            permissionChecker = get()
        )
    }
    factory {
        MoviesRepository(
            regionRepository = get(),
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}
private val useCasesModule = module {
    factory {
        FindMovieUseCase(
            moviesRepository = get()
        )

    }
    factory {
        GetPopularMoviesUseCase(
            moviesRepository = get()
        )
    }

    factory {
        RequestPopularMoviesUseCase(
            moviesRepository = get()
        )
    }
    factory {
        SwitchMovieFavoriteUseCase(
            moviesRepository = get()
        )
    }
}
