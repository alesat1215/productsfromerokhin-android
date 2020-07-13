package com.alesat1215.productsfromerokhin.data

import com.alesat1215.productsfromerokhin.data.local.GroupDB
import com.alesat1215.productsfromerokhin.data.local.ProductDB
import com.alesat1215.productsfromerokhin.data.local.Titles

interface IRemoteData {
    /** Titles from remote data */
    fun titles(): Titles
    /** Groups from remote data */
    fun groups(): List<GroupDB>
    /** Products from remote data with group name */
    fun products(): List<ProductDB>
}

/** Model for data from remote db */
data class RemoteData(
    val title: String? = null,
    val img: String? = null,
    val imgTitle: String? = null,
    val productsTitle: String? = null,
    val productsTitle2: String? = null,
    val groups: List<GroupRemote>? = null
) : IRemoteData {
    // Transform remote data to local for save in db
    override fun titles() = Titles(title, img, imgTitle, productsTitle, productsTitle2)
    override fun groups() = groups?.map { it.groupDB() }.orEmpty()
    override fun products() = groups?.map { it.productsDB() }?.flatten().orEmpty()
}
/** Group from remote db */
data class GroupRemote(
    val name: String? = null,
    var products: List<ProductRemote>? = null
) {
    /** Transform to group for local db */
    fun groupDB() = GroupDB(name)
    /** Transform to products for local db with group name */
    fun productsDB() = products?.map { it.productDB(name) }.orEmpty()
}
/** Product from remote db */
data class ProductRemote(
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null,
    val inStart: Boolean = false,
    val inStart2: Boolean = false
) {
    /** Transform to product for local db with group name */
    fun productDB(group: String?) =
        ProductDB(group, name, consist, img, price, inStart, inStart2)
}