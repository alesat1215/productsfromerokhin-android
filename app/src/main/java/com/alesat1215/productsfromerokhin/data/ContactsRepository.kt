package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/** Repository for phone number for order.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
interface IContactsRepository {
    /** Get contacts & update Room from remote config if needed */
    fun contacts(): LiveData<Contacts?>
}

@Singleton
class ContactsRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IContactsRepository {
    /** @return LiveData with phone from Room only once */
    private val contacts by lazy { db.contactsDao().contacts() }

    override fun contacts(): LiveData<Contacts?> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updatePhone)) { contacts }
    }

    /** Get phone from remote config & update db in background */
    private suspend fun updatePhone() = withContext(Dispatchers.Default) {
        // Get phone from remote config
        val contacts = gson.fromJson(dbUpdater.firebaseRemoteConfig.getString(CONTACTS), Contacts::class.java)
        Logger.d("Fetch from remote config phone: ${contacts.phone}, address: ${contacts.address}")
        // Update phone
        db.contactsDao().updateContacts(contacts)
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val CONTACTS = "contacts"
    }
}