package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.GroupRemote
import com.alesat1215.productsfromerokhin.data.ProductRemote
import com.alesat1215.productsfromerokhin.data.RemoteData
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.data.local.ProductInCart
import com.alesat1215.productsfromerokhin.data.local.Profile

object RemoteDataMockTest {
    val data = RemoteData(
        "Title",
        "Img",
        "ImgTitle",
        "ProductsTitle",
        "ProductsTitle2",
        listOf(
            GroupRemote("group_1").apply { products = products123Test() },
            GroupRemote("group_2").apply { products = products456Test() }
        )
    )

    fun products123Test() = listOf(ProductRemote("product_1", price = 1), ProductRemote("product_2", price = 2), ProductRemote("product_3", price = 3))
    fun products456Test() = listOf(ProductRemote("product_4", price = 4), ProductRemote("product_5", price = 5), ProductRemote("product_6", price = 6))

    val productsNotEmptyCart by lazy { data.products().map { Product(it, listOf(ProductInCart(name = it.name))) } }
    val sumInProductsNotEmptyCart by lazy { productsNotEmptyCart.map { it.priceSumInCart() }.sum() }
}

fun profileMockTest() = Profile(name = "name", phone = "phone", address = "address")