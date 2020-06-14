package com.alesat1215.productsfromerokhin.di

import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepository): IProductsRepository
}