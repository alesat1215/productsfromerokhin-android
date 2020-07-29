package com.alesat1215.productsfromerokhin

//import com.alesat1215.productsfromerokhin.data.GroupRemote
//import com.alesat1215.productsfromerokhin.data.ProductRemote
//import com.alesat1215.productsfromerokhin.data.RemoteData
import com.alesat1215.productsfromerokhin.data.Profile

object RemoteDataMockAndroidTest {
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
}

fun profileMockAndroidTest() = Profile(
    name = "name",
    phone = "phone",
    address = "address"
)