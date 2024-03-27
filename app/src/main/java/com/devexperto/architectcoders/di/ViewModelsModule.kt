package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.ui.detail.DetailViewModelFactory
import com.devexperto.architectcoders.ui.main.MainViewModelFactory
import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object ViewModelsModule {

    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(
        getPopularMoviesUseCase = getPopularMoviesUseCase,
        requestPopularMoviesUseCase = requestPopularMoviesUseCase
    )

    @Provides
    fun provideDetailViewModelFactory(
        findMovieUseCase: FindMovieUseCase,
        switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
    ) = DetailViewModelFactory(
        movieId = -1,
        findMovieUseCase = findMovieUseCase,
        switchMovieFavoriteUseCase = switchMovieFavoriteUseCase
    )

}