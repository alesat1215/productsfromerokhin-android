package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.DataMock
import javax.inject.Inject

class PhoneRepositoryMock @Inject constructor() : IPhoneRepository {
    override fun phone(): LiveData<PhoneForOrder?> = MutableLiveData(DataMock.phone)
}