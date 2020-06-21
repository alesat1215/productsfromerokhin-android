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
    Group(1, "Group 1 Test").apply { products = products123AndroidTest() },
    Group(2, "Group 2 Test").apply { products = products456AndroidTest() }
) }

fun products123AndroidTest() = listOf(
    Product(1, name = "Product 1 Test", inStart = true),
    Product(2, name = "Product 2 Test", inStart = true),
    Product(3, name = "Product 3 Test", inStart = true)
)
fun products456AndroidTest() = listOf(
    Product(4, name = "Product 4 Test", inStart2 = true),
    Product(5, name = "Product 5 Test", inStart2 = true),
    Product(6, name = "Product 6 Test", inStart2 = true)
)