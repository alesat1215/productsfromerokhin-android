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

    // Group
    @Test
    fun productsWithGroup() {
        val groupName = "group name"
        val group = GroupDB(name = groupName).apply {
            products = listOf(ProductDB(), ProductDB())
        }
        group.productsWithGroup().forEach {
            assertEquals(it.group, groupName)
        }
    }

    // products
    @Test
    fun products() {
        val groupName1 = "group name 1"
        val groupName2 = "group name 2"
        val groups = listOf(
            GroupDB(groupName1).apply { products = listOf(ProductDB(), ProductDB()) },
            GroupDB(groupName2).apply { products = listOf(ProductDB(), ProductDB()) }
        )
        val result = products(groups)
        assertEquals(result[0].group, groupName1)
        assertEquals(result[1].group, groupName1)
        assertEquals(result[2].group, groupName2)
        assertEquals(result[3].group, groupName2)
    }
}