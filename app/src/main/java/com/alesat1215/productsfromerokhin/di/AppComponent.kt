package com.alesat1215.productsfromerokhin.di

import android.content.Context
import com.alesat1215.productsfromerokhin.App
import com.alesat1215.productsfromerokhin.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    LoadModule::class,
    StartModule::class,
    MenuModule::class,
    CartModule::class,
    RepositoryModule::class
])
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}