package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.data.local.ProductInCart
import com.alesat1215.productsfromerokhin.remoteDataMockAndroidTest
import javax.inject.Inject
import javax.inject.Singleton

/** For UI testing with dagger */
@Singleton
class ProductsRepositoryMock @Inject constructor() : IProductsRepository {

    private val data by lazy { remoteDataMockAndroidTest() }

    override fun products() = MutableLiveData(data.products().map {
        Product(it.group, it.name, it.consist, it.img, it.price, it.inStart, it.inStart2)
    })

    override fun titles() = MutableLiveData(data.titles())

    override fun groups() = MutableLiveData(data.groups())

    override val productsInCart = MutableLiveData<List<ProductInCart>>(emptyList())
}