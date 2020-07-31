package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.alesat1215.productsfromerokhin.util.RemoteConfig
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface ITutorialRepository {
    /** @return instructions for order & update Room from remote config if needed */
    fun instructions(): LiveData<List<Instruction>>
}
/** Repository for instructions.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
@Singleton
class TutorialRepository @Inject constructor(
    /** Firebase remote config */
    private val remoteConfig: RemoteConfig,
    /** Room database */
    private val db: AppDatabase,
    /** Limiting the frequency of update database */
    private val limiter: RateLimiter,
    /** For parse JSON from remote config */
    private val gson: Gson
) : ITutorialRepository {
    /** Get instructions from Room only once */
    private val instructions by lazy { db.instructionsDao().instructions() }

    override fun instructions(): LiveData<List<Instruction>> {
        return Transformations.switchMap(updateDB()) { instructions }
    }
    /** Update Room from remote config if needed */
    private fun updateDB(): LiveData<Result<Unit>> {
        // Return if limit is over
        if (!limiter.shouldFetch()) return MutableLiveData(Result.success(Unit))
        // Fetch data from remote config & update db
        return Transformations.map(remoteConfig.fetchAndActivate()) {
            it.onSuccess { updateInstructions() }
            it.onFailure { Logger.d("Fetch remote config FAILED: ${it.localizedMessage}") }
            it
        }
    }
    /** Get instructions from remote config & update db in background */
    private fun updateInstructions() {
        // Update data in Room
        GlobalScope.launch(Dispatchers.IO) {
            // Get instructions from JSON
            val remoteInstructions = gson.fromJson(remoteConfig.firebaseRemoteConfig.getString(INSTRUCTIONS), Array<Instruction>::class.java).asList()
            Logger.d("Fetch instructions from remote config: ${remoteInstructions.count()}")
            // Update Room
            db.instructionsDao().updateInstructions(remoteInstructions)
        }
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val INSTRUCTIONS = "instructions"
    }

}