package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.tutorial.TutorialFragment
import com.alesat1215.productsfromerokhin.tutorial.TutorialViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TutorialModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun tutorialFragment(): TutorialFragment

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel::class)
    abstract fun bindViewModel(viewModel: TutorialViewModel): ViewModel
}