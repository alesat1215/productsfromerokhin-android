package com.alesat1215.productsfromerokhin.data

//import com.alesat1215.productsfromerokhin.RemoteDataMockTest
import org.junit.Test

import org.junit.Assert.*

class RemoteDataTest {

//    private val remoteData by lazy { RemoteDataMockTest.data }
//
//    //RemoteData
//    @Test
//    fun titles() {
//        val titles = remoteData.titles()
//        assertEquals(titles.title, remoteData.title)
//        assertEquals(titles.img, remoteData.img)
//        assertEquals(titles.imgTitle, remoteData.imgTitle)
//        assertEquals(titles.productsTitle, remoteData.productsTitle)
//        assertEquals(titles.productsTitle2, remoteData.productsTitle2)
//    }
//
//    @Test
//    fun groups() {
//        val groupsDB = remoteData.groups()
//        groupsDB.withIndex().forEach {
//            assertEquals(it.value.name, remoteData.groups?.get(it.index)?.name)
//        }
//    }
//
//    @Test
//    fun products() {
//        val products = remoteData.groups?.map { it.productsDB() }?.flatten()
//        assertEquals(remoteData.products(), products)
//    }
//
//    //GroupRemote
//    @Test
//    fun groupDB() {
//        val group = GroupRemote("name")
//        assertEquals(group.name, group.groupDB().name)
//    }
//
//    @Test
//    fun productsDB() {
//        val groupName = "groupName"
//        val products = RemoteDataMockTest.products123Test()
//        val group = GroupRemote("groupName", products)
//        val productsDB = group.productsDB()
//        productsDB.withIndex().forEach {
//            assertEquals(it.value.group, groupName)
//            assertEquals(it.value.name, products[it.index].name)
//        }
//    }
//
//    // ProductRemote
//    @Test
//    fun productDB() {
//        val product = ProductRemote("name", "consist", "img", 100, true, true)
//        val group = "group"
//        val productDB = product.productDB(group)
//        assertEquals(product.name, productDB.name)
//        assertEquals(product.consist, productDB.consist)
//        assertEquals(product.img, productDB.img)
//        assertEquals(product.price, productDB.price)
//        assertEquals(product.inStart, productDB.inStart)
//        assertEquals(product.inStart2, productDB.inStart2)
//        assertEquals(group, productDB.group)
//    }
}