package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.aboutproducts.AboutProductsFragment
import com.alesat1215.productsfromerokhin.aboutproducts.AboutProductsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AboutProductsModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun aboutProductsFragment(): AboutProductsFragment

    @Binds
    @IntoMap
    @ViewModelKey(AboutProductsViewModel::class)
    abstract fun bindViewModel(viewModel: AboutProductsViewModel): ViewModel
}