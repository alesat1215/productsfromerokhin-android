package com.alesat1215.productsfromerokhin.di

import com.alesat1215.productsfromerokhin.TestApp
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.ProductsRepositoryMock
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/** Fro UI test with dagger */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, StartModule::class, TestRepositoryModule::class])
interface TestAppComponent : AndroidInjector<TestApp>

/** Provide mock repository for UI test */
@Module
abstract class TestRepositoryModule {
    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepositoryMock): IProductsRepository
}