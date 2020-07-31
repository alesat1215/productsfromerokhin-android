package com.alesat1215.productsfromerokhin

import com.alesat1215.productsfromerokhin.data.*

object DataMock {
    val titles = Titles("title", "img", "imgTitle", "productsTitle", "productsTitle2")
    val groups = listOf(
        Group("group 1").apply { products = listOf(Product(name = "product 1"), Product(name = "product 2", inStart = true)) },
        Group("group 2").apply { products = listOf(Product(name = "product 3"), Product(name = "product 4", inStart2 = true)) },
        Group("group 3").apply { products = listOf(Product(name = "product 5"), Product(name = "product 6")) }
    )
    val products = products(groups).map { ProductInfo(it, listOf(ProductInCart(name = it.name))) }
    val profile = Profile(name = "name", phone = "phone", address = "address")
}