package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.DataMock
import javax.inject.Inject
import javax.inject.Singleton

/** For UI testing with dagger */
@Singleton
class ContactsRepositoryMock @Inject constructor() : IContactsRepository {
    override fun contacts(): LiveData<Contacts?> = MutableLiveData(DataMock.contacts)
}