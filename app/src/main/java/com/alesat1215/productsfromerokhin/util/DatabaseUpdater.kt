package com.alesat1215.productsfromerokhin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.orhanobut.logger.Logger
import javax.inject.Inject
import kotlin.Exception

interface IDatabaseUpdater {
    val firebaseRemoteConfig: FirebaseRemoteConfig
    fun updateDB(insertData: suspend () -> Unit): LiveData<Unit>
}

/** Update db from remote config if limit is not over & fetch is success */
class DatabaseUpdater @Inject constructor(
    /** Limiting the frequency of queries to remote config & update db */
    private val limiter: UpdateLimiter,
    /** Firebase remote config */
    private val remoteConfig: RemoteConfig
) : IDatabaseUpdater {
    override val firebaseRemoteConfig = remoteConfig.firebaseRemoteConfig
    /** Exec "insertData" if need update db */
    fun needUpdate(): LiveData<Result<Unit>> {
        /** Return if limit is over */
        if(limiter.needUpdate().not()) return MutableLiveData(Result.failure(Exception()))
        // Fetch data from remote config & update db
        return Transformations.switchMap(remoteConfig.fetchAndActivate()) {
            liveData {
                it.onSuccess { emit(Result.success(Unit)) }
                it.onFailure {
                    Logger.d("Fetch remote config FAILED: ${it.localizedMessage}")
                    emit(Result.failure(it))
                }
            }
        }
    }

    override fun updateDB(insertData: suspend () -> Unit): LiveData<Unit> {
        return Transformations.switchMap(needUpdate()) {
            liveData {
                it.onSuccess { emit(insertData()) }
                it.onFailure { emit(Unit) }
            }
        }
    }
}