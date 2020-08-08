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
interface IAboutAppRepository {
    /** Get app info & update Room from remote config if needed */
    fun aboutApp(): LiveData<AboutApp?>
}

@Singleton
class AboutAboutAppRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : IAboutAppRepository {
    /** @return LiveData with phone from Room only once */
    private val aboutApp by lazy { db.aboutApp().aboutApp() }

    override fun aboutApp(): LiveData<AboutApp?> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateAboutApp)) { aboutApp }
    }

    /** Get app info from remote config & update db in background */
    private suspend fun updateAboutApp() = withContext(Dispatchers.Default) {
        // Get phone from remote config
        val aboutApp = gson.fromJson(dbUpdater.firebaseRemoteConfig.getString(ABOUT_APP), AboutApp::class.java)
        Logger.d("Fetch from remote config privacy: ${aboutApp.privacy}, versionCode: ${aboutApp.versionCode}")
        // Update phone
        db.aboutApp().updateAboutApp(aboutApp)
    }

    companion object {
        const val ABOUT_APP = "about_app"
    }
}