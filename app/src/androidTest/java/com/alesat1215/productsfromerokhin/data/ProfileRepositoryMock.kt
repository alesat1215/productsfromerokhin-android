package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.DataMock
import javax.inject.Inject

class ProfileRepositoryMock @Inject constructor() : IProfileRepository {
    override val profile: LiveData<Profile> = MutableLiveData(DataMock.profile)

    override suspend fun updateProfile(profile: Profile) {
        TODO("Not yet implemented")
    }
}