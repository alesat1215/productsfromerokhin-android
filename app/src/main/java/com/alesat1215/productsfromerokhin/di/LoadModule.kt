package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.load.LoadFragment
import com.alesat1215.productsfromerokhin.load.LoadViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class LoadModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun loadFragment(): LoadFragment

    @Binds
    @IntoMap
    @ViewModelKey(LoadViewModel::class)
    abstract fun bindViewModel(viewModel: LoadViewModel): ViewModel
}