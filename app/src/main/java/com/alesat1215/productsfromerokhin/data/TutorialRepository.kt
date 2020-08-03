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

interface ITutorialRepository {
    /** @return instructions for order & update Room from remote config if needed */
    fun instructions(): LiveData<List<Instruction>>
}
/** Repository for instructions.
 * Return LiveData from Room & update if needed Room from remote config.
 * */
@Singleton
class TutorialRepository @Inject constructor(
//    /** Firebase remote config */
//    override val remoteConfig: RemoteConfig,
    /** Room database */
    private val db: AppDatabase,
//    /** Limiting the frequency of queries to remote config & update db */
//    override val limiter: UpdateLimiter,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) : ITutorialRepository {
    /** @return LiveData with instructions from Room only once */
    private val instructions by lazy { db.instructionsDao().instructions() }

    override fun instructions(): LiveData<List<Instruction>> {
        return Transformations.switchMap(dbUpdater.updateDatabase(::updateInstructions)) { instructions }
    }
    /** Update Room from remote config if needed */
//    private fun updateDB(): LiveData<Result<Unit>> {
//        // Return if limit is over
//        if (limiter.needUpdate().not()) return MutableLiveData(Result.success(Unit))
//        // Fetch data from remote config & update db
//        return Transformations.switchMap(remoteConfig.fetchAndActivate()) {
//            liveData {
//                it.onSuccess { emit(Result.success(updateInstructions())) }
//                it.onFailure {
//                    Logger.d("Fetch remote config FAILED: ${it.localizedMessage}")
//                    emit(Result.failure(it))
//                }
//            }
//        }
//    }
    /** Update data in Room in background */
    private suspend fun updateInstructions() = withContext(Dispatchers.Default) {
        // Get instructions from remote config
        val remoteInstructions = gson.fromJson(dbUpdater.firebaseRemoteConfig.getString(INSTRUCTIONS), Array<Instruction>::class.java).asList()
        Logger.d("Fetch instructions from remote config: ${remoteInstructions.count()}")
        // Update Room
        db.instructionsDao().updateInstructions(remoteInstructions)
    }

    companion object {
        /** Parameters in Firebase remote config */
        const val INSTRUCTIONS = "instructions"
    }

}