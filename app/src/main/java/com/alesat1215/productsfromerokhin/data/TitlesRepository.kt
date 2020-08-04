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

interface ITitlesRepository {
    /** Get titles & update Room from remote config if needed */
    fun titles(): LiveData<Titles?>
}

/** Repository for titles.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
@Singleton
class TitlesRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : ITitlesRepository {
    /** @return LiveData with titles from Room only once */
    private val titles by lazy { db.titlesDao().titles() }

    /** Get titles & update Room from remote config if needed */
    override fun titles(): LiveData<Titles?> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateTitles)) { titles }
    }
    /** Get groups, products & titles from remote config & update db in background */
    private suspend fun updateTitles() = withContext(Dispatchers.Default) {
        // Get titles from JSON
        val titles = gson.fromJson(
            dbUpdater.firebaseRemoteConfig.getString(TITLES),
            Titles::class.java
        )
        Logger.d("Fetch from remote config titles: " +
                "${titles.title}, ${titles.imgTitle}, ${titles.productsTitle}, ${titles.productsTitle2}, ${titles.img}")
        // Update titles
        db.titlesDao().updateTitles(titles)
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val TITLES = "titles"
    }
}