package com.alesat1215.productsfromerokhin.contacts

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.IContactsRepository
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: IContactsRepository
) : ViewModel() {
    val contacts by lazy { contactsRepository.contacts() }
}