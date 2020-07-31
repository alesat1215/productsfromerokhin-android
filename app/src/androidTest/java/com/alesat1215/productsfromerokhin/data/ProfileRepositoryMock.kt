package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class ProfileRepositoryMock @Inject constructor() : IProfileRepository {
    override val profile: LiveData<Profile>
        get() = TODO("Not yet implemented")

    override suspend fun updateProfile(profile: Profile) {
        TODO("Not yet implemented")
    }
}