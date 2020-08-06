package com.alesat1215.productsfromerokhin.di

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.contacts.ContactsFragment
import com.alesat1215.productsfromerokhin.contacts.ContactsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ContactsModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contactsFragment(): ContactsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindViewModel(viewModel: ContactsViewModel): ViewModel
}