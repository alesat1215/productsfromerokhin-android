package com.alesat1215.productsfromerokhin.di

import com.alesat1215.productsfromerokhin.TestApp
import com.alesat1215.productsfromerokhin.data.*
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/** Fro UI test with dagger */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    LoadModule::class,
    StartModule::class,
    MenuModule::class,
    CartModule::class,
    ProfileModule::class,
    TutorialModule::class,
    AboutProductsModule::class,
    ContactsModule::class,
    AboutAppModule::class,
    TestRepositoryModule::class,
    HelpModule::class
])
interface TestAppComponent : AndroidInjector<TestApp>

/** Provide mock repository for UI test */
@Module
abstract class TestRepositoryModule {
    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepositoryMock): IProductsRepository
    @Binds
    abstract fun bindTutorialRepository(repository: TutorialRepositoryMock): ITutorialRepository
    @Binds
    abstract fun bindProfileRepository(repository: ProfileRepositoryMock): IProfileRepository
    @Binds
    abstract fun bindPhoneRepository(repository: ContactsRepositoryMock): IContactsRepository
    @Binds
    abstract fun bindTitlesRepository(repository: TitlesRepositoryMock): ITitlesRepository
    @Binds
    abstract fun bindAboutProductsRepository(repository: AboutProductsRepositoryMock): IAboutProductsRepository
    @Binds
    abstract fun bindAboutAppRepository(repository: AboutAppRepositoryMock): IAboutAppRepository
}

@Module
class HelpModule {
    @Provides
    fun firebaseAuth() = FirebaseAuth.getInstance()
}