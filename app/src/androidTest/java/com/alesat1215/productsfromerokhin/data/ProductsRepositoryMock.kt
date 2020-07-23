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
        Product(it, listOf(ProductInCart(name = it.name)))
    })

    override fun titles() = MutableLiveData(data.titles())

    override fun groups() = MutableLiveData(data.groups())

    override val productsInCart = MutableLiveData<List<Product>>(emptyList())

    override suspend fun addProductToCart(product: ProductInCart) {
        TODO("Not yet implemented")
    }

    override suspend fun delProductFromCart(product: ProductInCart) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        TODO("Not yet implemented")
    }
}