package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.RemoteData

fun remoteDataMockTest() = RemoteData(
    "Title",
    "Img",
    "ImgTitle",
    "ProductsTitle",
    "ProductsTitle2"
).apply { groups = listOf(
    Group(1).apply { products = products123Test() },
    Group(2).apply { products = products456Test() }
) }

fun products123Test() = listOf(Product(1), Product(2), Product(3))
fun products456Test() = listOf(Product(4), Product(5), Product(6))