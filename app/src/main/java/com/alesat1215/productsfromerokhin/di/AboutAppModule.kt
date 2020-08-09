package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.aboutapp.AboutAppFragment
import com.alesat1215.productsfromerokhin.aboutapp.AboutAppViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AboutAppModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun aboutAppFragment(): AboutAppFragment

    @Binds
    @IntoMap
    @ViewModelKey(AboutAppViewModel::class)
    abstract fun bindViewModel(viewModel: AboutAppViewModel): ViewModel
}