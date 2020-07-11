package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.cart.CartFragment
import com.alesat1215.productsfromerokhin.cart.CartViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CartModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun cartFragment(): CartFragment

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindViewModel(viewModel: CartViewModel): ViewModel
}