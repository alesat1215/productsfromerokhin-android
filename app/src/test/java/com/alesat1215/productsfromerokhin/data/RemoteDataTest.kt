package com.alesat1215.productsfromerokhin.data

import com.alesat1215.productsfromerokhin.products123Test
import com.alesat1215.productsfromerokhin.remoteDataMockTest
import org.junit.Test

import org.junit.Assert.*

class RemoteDataTest {

    //RemoteData
    @Test
    fun titles() {
        val remoteData = remoteDataMockTest()
        val titles = remoteData.titles()
        assertEquals(titles.title, remoteData.title)
        assertEquals(titles.img, remoteData.img)
        assertEquals(titles.imgTitle, remoteData.imgTitle)
        assertEquals(titles.productsTitle, remoteData.productsTitle)
        assertEquals(titles.productsTitle2, remoteData.productsTitle2)
    }

    @Test
    fun groups() {
        val remoteData = remoteDataMockTest()
        val groupsDB = remoteData.groups()
        groupsDB.withIndex().forEach {
            assertEquals(it.value.name, remoteData.groups?.get(it.index)?.name)
        }
    }

    //GroupRemote
    @Test
    fun groupDB() {
        val group = GroupRemote("name")
        assertEquals(group.name, group.groupDB().name)
    }

    @Test
    fun productsDB() {
        val groupName = "groupName"
        val products = products123Test()
        val group = GroupRemote("groupName", products)
        val productsDB = group.productsDB()
        productsDB.withIndex().forEach {
            assertEquals(it.value.group, groupName)
            assertEquals(it.value.name, products[it.index].name)
        }
    }

    // ProductRemote
    @Test
    fun productDB() {
        val product = ProductRemote("name", "consist", "img", 100, true, true)
        val group = "group"
        val productDB = product.productDB(group)
        assertEquals(product.name, productDB.name)
        assertEquals(product.consist, productDB.consist)
        assertEquals(product.img, productDB.img)
        assertEquals(product.price, productDB.price)
        assertEquals(product.inStart, productDB.inStart)
        assertEquals(product.inStart2, productDB.inStart2)
        assertEquals(group, productDB.group)
    }


//    @Test
//    fun productsWithGroupId() {
//        val products = listOf(Product(id = 1), Product(id = 2), Product(id = 3))
//        val products2 = listOf(Product(id = 4), Product(id = 5), Product(id = 6))
//        val groups = listOf(
//            Group(id = 1).apply { this.products = products },
//            Group(id = 2).apply { this.products = products2 }
//        )
//        val data = RemoteData()//.apply { this.groups = groups }
//        var result = data.productsWithGroupId()
//        assertNull(result)
//        data.groups = groups
//        result = data.productsWithGroupId()
//        assertNotNull(result)
//        assertEquals(result?.count(), products.count() + products2.count())
//        var group = groups.first()
//        var productsGroup = result!!.take(group.products!!.count())
//        productsGroup.forEach { assertEquals(it.group, group.id) }
//        group = groups.last()
//        productsGroup = result.takeLast(group.products!!.count())
//        productsGroup.forEach { assertEquals(it.group, group.id) }
//    }
}