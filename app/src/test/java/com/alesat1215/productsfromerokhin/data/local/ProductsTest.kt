package com.alesat1215.productsfromerokhin.data.local

import org.junit.Test

import org.junit.Assert.*

class ProductsTest {
    // Product
    private val product = Product(
        ProductDB(name = "productDB", price = 100),
        listOf(ProductInCart(name = "productDB"), ProductInCart(name = "productDB"))
    )

    @Test
    fun asProductInCart() {
        assertEquals(product.productDB?.name, product.asProductInCart().name)
    }

    @Test
    fun priceSumInCart() {
        assertEquals(product.priceSumInCart(), 200)
    }

    @Test
    fun textForOrder() {
        assertTrue(product.textForOrder().contains(product.productDB?.name.orEmpty()))
        assertTrue(product.textForOrder().contains(product.productDB?.price.toString()))
        assertTrue(product.textForOrder().contains(product.inCart.count().toString()))
        assertTrue(product.textForOrder().contains(product.priceSumInCart().toString()))
    }
}