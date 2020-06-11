package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.RemoteData

fun remoteDataMockAndroidTest() = RemoteData(
    "Title",
    "Img",
    "ImgTitle",
    "ProductsTitle",
    "ProductsTitle2"
).apply { groups = listOf(
    Group(1).apply { products = products123AndroidTest() },
    Group(2).apply { products = products456AndroidTest() }
) }

fun products123AndroidTest() = listOf(Product(1), Product(2), Product(3))
fun products456AndroidTest() = listOf(Product(4), Product(5), Product(6))