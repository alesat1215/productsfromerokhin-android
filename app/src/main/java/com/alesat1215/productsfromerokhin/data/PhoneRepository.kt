package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/** Repository for phone number for order.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
interface IPhoneRepository {
    /** Get phone & update Room from remote config if needed */
    fun phone(): LiveData<PhoneForOrder?>
}

@Singleton
class PhoneRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater
) : IPhoneRepository {
    /** @return LiveData with phone from Room only once */
    private val phone by lazy { db.phoneDao().phone() }

    override fun phone(): LiveData<PhoneForOrder?> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updatePhone)) { phone }
    }

    /** Get phone from remote config & update db in background */
    private suspend fun updatePhone() = withContext(Dispatchers.Default) {
        // Get phone from remote config
        val phone = dbUpdater.firebaseRemoteConfig.getString(PHONE)
        Logger.d("Fetch from remote config phone for order: $phone")
        // Update phone
        db.phoneDao().updatePhone(PhoneForOrder(phone))
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val PHONE = "phone"
    }
}