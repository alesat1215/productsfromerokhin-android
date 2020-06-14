package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.menu.MenuFragment
import com.alesat1215.productsfromerokhin.menu.MenuViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MenuModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun menuFragment(): MenuFragment

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    abstract fun bindViewModel(viewModel: MenuViewModel): ViewModel
}