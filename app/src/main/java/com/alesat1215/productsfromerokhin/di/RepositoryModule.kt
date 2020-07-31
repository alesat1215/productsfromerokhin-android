package com.alesat1215.productsfromerokhin.di

import com.alesat1215.productsfromerokhin.data.*
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepository): IProductsRepository
    @Binds
    abstract fun bindTutorialRepository(repository: TutorialRepository): ITutorialRepository
    @Binds
    abstract fun bindProfileRepository(repository: ProfileRepository): IProfileRepository
    @Binds
    abstract fun bindPhoneRepository(repository: PhoneRepository): IPhoneRepository
}