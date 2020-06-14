package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.RemoteData

fun remoteDataMockAndroidTest() = RemoteData(
    "Title Test",
    "Img Test",
    "ImgTitle Test",
    "ProductsTitle Test",
    "ProductsTitle2 Test"
).apply { groups = listOf(
    Group(1).apply { products = products123AndroidTest() },
    Group(2).apply { products = products456AndroidTest() }
) }

fun products123AndroidTest() = listOf(
    Product(1, name = "Product 1 Test"),
    Product(2, name = "Product 2 Test"),
    Product(3, name = "Product 3 Test")
)
fun products456AndroidTest() = listOf(
    Product(4, name = "Product 4 Test"),
    Product(5, name = "Product 5 Test"),
    Product(6, name = "Product 6 Test")
)