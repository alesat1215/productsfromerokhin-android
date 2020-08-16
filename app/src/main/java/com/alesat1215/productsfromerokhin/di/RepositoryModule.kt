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
    abstract fun bindContactsRepository(repository: ContactsRepository): IContactsRepository
    @Binds
    abstract fun bindTitlesRepository(repository: TitlesRepository): ITitlesRepository
    @Binds
    abstract fun bindAboutProductsRepository(repository: AboutProductsRepository): IAboutProductsRepository
    @Binds
    abstract fun bindAboutAppRepository(repository: AboutAppRepository): IAboutAppRepository
    @Binds
    abstract fun bindOrderWarningRepository(repository: OrderWarningRepository): IOrderWarningRepository
}