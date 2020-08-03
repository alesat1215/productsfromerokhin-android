package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.orhanobut.logger.Logger
import javax.inject.Inject
import kotlin.Exception

/** Update db from remote config if limit is not over & fetch is success */
class DatabaseUpdater @Inject constructor(
    /** Limiting the frequency of queries to remote config & update db */
    private val limiter: UpdateLimiter,
    /** Firebase remote config */
    private val remoteConfig: RemoteConfig
) {
    val firebaseRemoteConfig = remoteConfig.firebaseRemoteConfig
    /** Check needed update db by limiter & fetching */
    private fun needUpdate(): LiveData<Result<Unit>> {
        /** Return failure if limit is over */
        if (limiter.needUpdate().not()) return MutableLiveData(Result.failure(Exception()))
        // Fetch & activate data from remote config
        return Transformations.switchMap(remoteConfig.fetchAndActivate()) {
            liveData {
                it.onSuccess { emit(Result.success(Unit)) }
                it.onFailure {
                    Logger.d("Fetch remote config FAILED: ${it.localizedMessage}")
                    limiter.reset()
                    emit(Result.failure(it))
                }
            }
        }
    }
    /** Exec "insertData" if need update db */
    fun updateDatabase(insertData: suspend () -> Unit): LiveData<Unit> {
        return Transformations.switchMap(needUpdate()) {
            liveData {
                it.onSuccess { emit(insertData()) }
                it.onFailure { emit(Unit) }
            }
        }
    }
}