package com.alesat1215.productsfromerokhin.data.local

import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.ProductInCart
import com.alesat1215.productsfromerokhin.data.ProductInfo
import org.junit.Test

import org.junit.Assert.*

class ProductsTest {
    // Product
    private val product = ProductInfo(
        Product(
            name = "productDB",
            price = 100
        ),
        listOf(
            ProductInCart(name = "productDB"),
            ProductInCart(name = "productDB")
        )
    )

    @Test
    fun asProductInCart() {
        assertEquals(product.product?.name, product.asProductInCart().name)
    }

    @Test
    fun priceSumInCart() {
        assertEquals(product.priceSumInCart(), 200)
    }

    @Test
    fun textForOrder() {
        assertTrue(product.textForOrder().contains(product.product?.name.orEmpty()))
        assertTrue(product.textForOrder().contains(product.product?.price.toString()))
        assertTrue(product.textForOrder().contains(product.inCart.count().toString()))
        assertTrue(product.textForOrder().contains(product.priceSumInCart().toString()))
    }

    // Group
    @Test
    fun productsWithGroup() {
        val groupName = "group name"
        val group = Group(name = groupName)
            .apply {
            products = listOf(
                Product(),
                Product()
            )
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
            Group(groupName1)
                .apply { products = listOf(
                    Product(),
                    Product()
                ) },
            Group(groupName2)
                .apply { products = listOf(
                    Product(),
                    Product()
                ) }
        )
        val result = com.alesat1215.productsfromerokhin.data.products(groups)
        assertEquals(result[0].group, groupName1)
        assertEquals(result[1].group, groupName1)
        assertEquals(result[2].group, groupName2)
        assertEquals(result[3].group, groupName2)
    }
}