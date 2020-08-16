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

/** Repository for order warning.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
interface IOrderWarningRepository {
    /** Get order warning & update Room from remote config if needed */
    fun orderWarning(): LiveData<OrderWarning?>
}

@Singleton
class OrderWarningRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IOrderWarningRepository {
    /** @return LiveData with order warning from Room only once */
    private val orderWarning by lazy { db.orderWarningDao().orderWarning() }

    override fun orderWarning(): LiveData<OrderWarning?> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateOrderWarning)) { orderWarning }
    }

    private suspend fun updateOrderWarning() = withContext(Dispatchers.Default) {
        // Get warning from JSON
        val warning = gson.fromJson(dbUpdater.firebaseRemoteConfig.getString(ORDER_WARNING), OrderWarning::class.java)
        Logger.d("Fetch from remote config order warning: ${warning.text}, for: ${warning.groups}")
        // Update warning
        db.orderWarningDao().updateOrderWarning(warning)
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val ORDER_WARNING = "order_warning"
    }
}