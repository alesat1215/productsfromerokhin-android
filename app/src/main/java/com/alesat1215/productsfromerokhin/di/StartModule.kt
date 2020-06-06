package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.start.StartFragment
import com.alesat1215.productsfromerokhin.start.StartViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class StartModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun startFragment(): StartFragment

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindViewModel(viewModel: StartViewModel): ViewModel
}