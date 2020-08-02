package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.alesat1215.productsfromerokhin.util.UpdateLimiter
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
    /** Firebase remote config */
    private val remoteConfig: RemoteConfig,
    /** Room database */
    private val db: AppDatabase,
    /** Limiting the frequency of queries to remote config & update db */
    private val limiter: UpdateLimiter,
    /** For parse JSON from remote config */
    private val gson: Gson
) : ITitlesRepository {
    /** @return LiveData with titles from Room only once */
    private val titles by lazy { db.titlesDao().titles() }

    /** Get titles & update Room from remote config if needed */
    override fun titles(): LiveData<Titles?> {
        return Transformations.switchMap(updateDB()) { titles }
    }
    /** Update Room from remote config if needed */
    private fun updateDB(): LiveData<Result<Unit>> {
        /** Return if limit is over */
        if(limiter.needUpdate().not()) return MutableLiveData(Result.success(Unit))
        // Fetch data from remote config & update db
        return Transformations.switchMap(remoteConfig.fetchAndActivate()) {
            liveData {
                it.onSuccess { emit(Result.success(updateTitles())) }
                it.onFailure {
                    Logger.d("Fetch remote config FAILED: ${it.localizedMessage}")
                    emit(Result.failure(it))
                }
            }
        }
    }
    /** Get groups, products & titles from remote config & update db in background */
    private suspend fun updateTitles() = withContext(Dispatchers.Default) {
        // Get titles from JSON
        val titles = gson.fromJson(
            remoteConfig.firebaseRemoteConfig.getString(TITLES),
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