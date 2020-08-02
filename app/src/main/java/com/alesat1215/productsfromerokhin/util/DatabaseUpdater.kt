package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.orhanobut.logger.Logger

/** Update db from remote config if limit is not over & fetch is success */
interface DatabaseUpdater {
    /** Limiting the frequency of queries to remote config & update db */
    val limiter: UpdateLimiter
    /** Firebase remote config */
    val remoteConfig: RemoteConfig
    /** Exec "insertData" if need update db */
    fun updateDB(insertData: suspend () -> Unit): LiveData<Result<Unit>> {
        /** Return if limit is over */
        if(limiter.needUpdate().not()) return MutableLiveData(Result.success(Unit))
        // Fetch data from remote config & update db
        return Transformations.switchMap(remoteConfig.fetchAndActivate()) {
            liveData {
                it.onSuccess { emit(Result.success(insertData())) }
                it.onFailure {
                    Logger.d("Fetch remote config FAILED: ${it.localizedMessage}")
                    emit(Result.failure(it))
                }
            }
        }
    }
}