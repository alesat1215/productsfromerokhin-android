package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.GroupRemote
import com.alesat1215.productsfromerokhin.data.ProductRemote
import com.alesat1215.productsfromerokhin.data.RemoteData
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.data.local.ProductInCart

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

    fun products123Test() = listOf(ProductRemote("product_1"), ProductRemote("product_2"), ProductRemote("product_3"))
    fun products456Test() = listOf(ProductRemote("product_4"), ProductRemote("product_5"), ProductRemote("product_6"))

    val productsNotEmptyCart by lazy { data.products().map { Product(it, listOf(ProductInCart(name = it.name))) } }
}