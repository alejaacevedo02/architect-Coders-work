package com.devexperto.architectcoders.ui.main

import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
object MainFragmentModule {
    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(
        getPopularMoviesUseCase = getPopularMoviesUseCase,
        requestPopularMoviesUseCase = requestPopularMoviesUseCase
    )

}

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent{
    val mainViewModelFactory : MainViewModelFactory
}