package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.ui.detail.DetailFragment
import com.devexperto.architectcoders.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, AppDataModule::class]
)
interface AppComponent {

    //Inject something in a component not in the constructor
    fun inject(fragment: MainFragment)

    fun inject(fragment: DetailFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}