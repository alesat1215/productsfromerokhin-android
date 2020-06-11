package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.RemoteData

fun remoteDataMock() = RemoteData(
    "Title",
    "Img",
    "ImgTitle",
    "ProductsTitle",
    "ProductsTitle2"
).apply { groups = listOf(
    Group(1).apply { products = products123() },
    Group(2).apply { products = products456() }
) }

fun products123() = listOf(Product(1), Product(2), Product(3))
fun products456() = listOf(Product(4), Product(5), Product(6))