package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.GroupRemote
import com.alesat1215.productsfromerokhin.data.ProductRemote
import com.alesat1215.productsfromerokhin.data.RemoteData

fun remoteDataMockTest() = RemoteData(
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