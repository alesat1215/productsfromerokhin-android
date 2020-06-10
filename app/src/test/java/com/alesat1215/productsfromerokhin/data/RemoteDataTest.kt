package com.alesat1215.productsfromerokhin.data

import org.junit.Test

import org.junit.Assert.*

class RemoteDataTest {

    @Test
    fun productsWithGroupId() {
        val products = listOf(Product(id = 1), Product(id = 2), Product(id = 3))
        val products2 = listOf(Product(id = 4), Product(id = 5), Product(id = 6))
        val groups = listOf(
            Group(id = 1).apply { this.products = products },
            Group(id = 2).apply { this.products = products2 }
        )
        val data = RemoteData()//.apply { this.groups = groups }
        var result = data.productsWithGroupId()
        assertNull(result)
        data.groups = groups
        result = data.productsWithGroupId()
        assertNotNull(result)
        assertEquals(result?.count(), products.count() + products2.count())
        var group = groups.first()
        var productsGroup = result!!.take(group.products!!.count())
        productsGroup.forEach { assertEquals(it.group, group.id) }
        group = groups.last()
        productsGroup = result.takeLast(group.products!!.count())
        productsGroup.forEach { assertEquals(it.group, group.id) }
    }
}