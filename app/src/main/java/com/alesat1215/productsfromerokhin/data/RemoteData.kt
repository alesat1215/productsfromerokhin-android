package com.alesat1215.productsfromerokhin.data

import android.util.Log
import com.alesat1215.productsfromerokhin.data.local.GroupDB
import com.alesat1215.productsfromerokhin.data.local.ProductDB
import com.alesat1215.productsfromerokhin.data.local.Titles

interface IRemoteData {
    fun asLocalData(): Triple<Titles, List<GroupDB>, List<ProductDB>>
}

data class RemoteData(
    val title: String? = null,
    val img: String? = null,
    val imgTitle: String? = null,
    val productsTitle: String? = null,
    val productsTitle2: String? = null,
    val groups: List<GroupRemote>? = null
) : IRemoteData {
    /** Transform remote data to local for save in db */
    override fun asLocalData(): Triple<Titles, List<GroupDB>, List<ProductDB>> {
        val groupsAndProducts = groupsAndProducts()
        return Triple(titles(), groupsAndProducts.first, groupsAndProducts.second)
    }

    private fun titles() = Titles(title, img, imgTitle, productsTitle, productsTitle2)

    private fun groupsAndProducts(): Pair<List<GroupDB>, List<ProductDB>> {
        val products = mutableListOf<ProductDB>()
        val groups = groups?.withIndex()?.map { indexedGroup ->
            val productsRemote = indexedGroup.value.products
            if (productsRemote != null) {
                products.addAll(productsRemote.map {
                    ProductDB(indexedGroup.index, it.name, it.consist, it.img, it.price, it.inStart, it.inStart2)
                })
            }
            Log.d("RemoteData", "Transform from remote to db group with products: ${indexedGroup.value.name}")
            GroupDB(indexedGroup.index, indexedGroup.value.name)
        } ?: emptyList()
        return Pair(groups, products)
    }
}

data class GroupRemote(
//    val id: Int = 0,
    val name: String? = null,
    var products: List<ProductRemote>? = null
)

data class ProductRemote(
//    val id: Int = 0,
    var group: Int = 0,
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null,
    val inStart: Boolean = false,
    val inStart2: Boolean = false
)

///**
// * Model for remote database & titles in Room
// * */
//@Fts4
//@Entity
//data class RemoteData(
//    val title: String? = null,
//    val img: String? = null,
//    val imgTitle: String? = null,
//    val productsTitle: String? = null,
//    val productsTitle2: String? = null
//) {
//    /** Only for remote database */
//    @Ignore var groups: List<Group>? = null
//
//    /** @return all products from all groups with group id */
//    fun productsWithGroupId(): List<Product>? = groups?.flatMap { group ->
//        group.products?.apply { map { it.group = group.id } } ?: emptyList()
//    }
//}

///**
// * Model for Group from remote database & Room
// * */
//@Fts4
//@Entity
//data class Group(
//    val id: Int = 0,
//    val name: String? = null
//) {
//    /** Only for remote database */
//    @Ignore var products: List<Product>? = null
//}

///**
// * Model for Product from remote database & Room
// * */
//@Fts4
//@Entity
//data class Product(
//    val id: Int = 0,
//    var group: Int = 0,
//    val name: String? = null,
//    val consist: String? = null,
//    val img: String? = null,
//    val price: Int? = null,
//    val inStart: Boolean = false,
//    val inStart2: Boolean = false
//)

///**
// * Model for Product in cart
// * */
//@Fts4
//@Entity
//data class ProductInCart(
//    val name: String? = null,
//    val consist: String? = null,
//    val img: String? = null,
//    val price: Int? = null,
//    val count: Int = 0
//)