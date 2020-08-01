package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    /** Firebase remote config */
    private val remoteConfig: RemoteConfig,
    /** Room database */
    private val db: AppDatabase,
    /** Limiting the frequency of queries to remote config & update db */
    private val limiter: UpdateLimiter
) : IPhoneRepository {
    /** @return LiveData with phone from Room only once */
    private val phone by lazy { db.phoneDao().phone() }

    override fun phone(): LiveData<PhoneForOrder?> {
        return Transformations.switchMap(updateDB()) { phone }
    }

    private fun updateDB(): LiveData<Result<Unit>> {
        /** Return if limit is over */
        if(limiter.needUpdate().not()) return MutableLiveData(Result.success(Unit))
        // Fetch data from remote config & update db
        return Transformations.map(remoteConfig.fetchAndActivate()) {
            it.onSuccess { updatePhone() }
            it.onFailure { Logger.d("Fetch remote config FAILED: ${it.localizedMessage}") }
            it
        }
    }

    private fun updatePhone() {
        // Update data in Room
        GlobalScope.launch(Dispatchers.IO) {
            // Get groups with products from JSON
            val phone = remoteConfig.firebaseRemoteConfig.getString(PHONE)
            Logger.d("Fetch from remote config phone for order: $phone")
            // Update phone
            db.phoneDao().updatePhone(PhoneForOrder(phone))
        }
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val PHONE = "phone"
    }
}