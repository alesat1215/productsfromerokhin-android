package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.*

object DataMock {
//    val data = RemoteData(
//    "Title Test",
//    "Img Test",
//    "ImgTitle Test",
//    "ProductsTitle Test",
//    "ProductsTitle2 Test",
//    listOf(
//        GroupRemote("Group 1 Test").apply { products = products123AndroidTest() },
//        GroupRemote("Group 2 Test").apply { products = products456AndroidTest() }
//    )
//    )
//
//    fun products123AndroidTest() = listOf(
//        ProductRemote(name = "Product 1 Test", inStart = true),
//        ProductRemote(name = "Product 2 Test", inStart = true),
//        ProductRemote(name = "Product 3 Test", inStart = true)
//    )
//    fun products456AndroidTest() = listOf(
//        ProductRemote(name = "Product 4 Test", inStart2 = true),
//        ProductRemote(name = "Product 5 Test", inStart2 = true),
//        ProductRemote(name = "Product 6 Test", inStart2 = true)
//    )
//
//    val productsEmptyCart by lazy { data.products().map {
//        Product(it, emptyList())
//    } }
//
//    val productsForCart by lazy { productsEmptyCart.map { it.asProductInCart() } }
    val titles = Titles("title", "img", "imgTitle", "productsTitle", "productsTitle2")
    val groups = listOf(
        Group("group 1").apply { products = listOf(Product(name = "product 1"), Product(name = "product 2", inStart = true)) },
        Group("group 2").apply { products = listOf(Product(name = "product 3"), Product(name = "product 4", inStart2 = true)) },
        Group("group 3").apply { products = listOf(Product(name = "product 5"), Product(name = "product 6")) }
    )
    val products = products(groups).map { ProductInfo(it, listOf(ProductInCart(name = it.name))) }
}

fun profileMockAndroidTest() = Profile(
    name = "name",
    phone = "phone",
    address = "address"
)