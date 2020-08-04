package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.DataMock
import javax.inject.Inject
import javax.inject.Singleton

/** For UI testing with dagger */
@Singleton
class ProductsRepositoryMock @Inject constructor() : IProductsRepository {

    override fun products() = MutableLiveData(DataMock.products)

    override fun groups() = MutableLiveData<List<Group>>(DataMock.groups)

    override val productsInCart = MutableLiveData<List<ProductInfo>>(DataMock.products)

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