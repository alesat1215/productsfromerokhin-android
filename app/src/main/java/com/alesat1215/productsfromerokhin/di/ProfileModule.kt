package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.profile.ProfileFragment
import com.alesat1215.productsfromerokhin.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ProfileModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun profileFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindViewModel(viewModel: ProfileViewModel): ViewModel
}