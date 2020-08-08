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

/** Repository for app info.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
interface IAppInfoRepository {
    /** Get appInfo & update Room from remote config if needed */
    fun appInfo(): LiveData<AppInfo?>
}

@Singleton
class AppInfoRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IAppInfoRepository {
    /** @return LiveData with phone from Room only once */
    private val appInfo by lazy { db.appInfoDao().appInfo() }

    override fun appInfo(): LiveData<AppInfo?> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateAppInfo)) { appInfo }
    }

    /** Get phone from remote config & update db in background */
    private suspend fun updateAppInfo() = withContext(Dispatchers.Default) {
        // Get phone from remote config
        val appInfo = gson.fromJson(dbUpdater.firebaseRemoteConfig.getString(APP_INFO), AppInfo::class.java)
        Logger.d("Fetch from remote config privacy: ${appInfo.privacy}, versionCode: ${appInfo.versionCode}")
        // Update phone
        db.appInfoDao().updateAppInfo(appInfo)
    }

    companion object {
        const val APP_INFO = "app_info"
    }
}