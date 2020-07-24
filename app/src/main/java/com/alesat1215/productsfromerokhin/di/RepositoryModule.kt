package com.alesat1215.productsfromerokhin.di

import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.ITutorialRepository
import com.alesat1215.productsfromerokhin.data.ProductsRepository
import com.alesat1215.productsfromerokhin.data.TutorialRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepository): IProductsRepository
    @Binds
    abstract fun bindTutorialRepository(repository: TutorialRepository): ITutorialRepository
}