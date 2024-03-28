package com.devexperto.architectcoders.ui.detail

import com.devexperto.architectcoders.usecases.FindMovieUseCase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailFragmentModule(private val movieId: Int) {
    @Provides
    fun provideDetailViewModelFactory(
        findMovieUseCase: FindMovieUseCase,
        switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
    ) = DetailViewModelFactory(
        movieId = movieId,
        findMovieUseCase = findMovieUseCase,
        switchMovieFavoriteUseCase = switchMovieFavoriteUseCase
    )


}

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent{
    val detailViewModelFactory : DetailViewModelFactory
}