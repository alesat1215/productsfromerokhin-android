package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.remoteDataMockAndroidTest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepositoryMock @Inject constructor() : IProductsRepository {

    private val data by lazy { remoteDataMockAndroidTest() }

    override fun products() = MutableLiveData(data.productsWithGroupId()!!)

    override fun titles() = MutableLiveData(data)

    override fun groups() = MutableLiveData(data.groups!!)
}