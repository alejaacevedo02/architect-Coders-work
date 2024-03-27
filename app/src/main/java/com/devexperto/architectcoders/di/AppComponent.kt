package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.ui.detail.DetailViewModelFactory
import com.devexperto.architectcoders.ui.main.MainViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Factory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [UseCasesModule::class, DataModule::class,
        AppModule::class, ViewModelsModule::class]
)
interface AppComponent {

    val mainViewModelFactory : MainViewModelFactory
    val detailViewModelFactory : DetailViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app:  Application) : AppComponent
    }
}