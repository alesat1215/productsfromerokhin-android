package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface IProfileRepository {
    /** @return LiveData with delivery info from Room only once */
    val profile: LiveData<Profile>
    /** Update profile data in db */
    suspend fun updateProfile(profile: Profile)
}

@Singleton
class ProfileRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase
) : IProfileRepository {

    override val profile by lazy { db.profileDao().profile() }

    override suspend fun updateProfile(profile: Profile) = withContext(Dispatchers.IO) {
        db.profileDao().updateProfile(profile)
    }
}