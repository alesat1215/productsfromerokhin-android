package com.alesat1215.productsfromerokhin.data

import androidx.room.*

/**
 * Model for remote database & titles in Room
 * */
@Fts4
@Entity
data class RemoteData(
    val title: String? = null,
    val img: String? = null,
    val imgTitle: String? = null,
    val productsTitle: String? = null,
    val productsTitle2: String? = null
) {
    /** Only for remote database */
    @Ignore var groups: List<Group>? = null

    /** @return all products from all groups with group id */
    fun productsWithGroupId(): List<Product>? = groups?.flatMap { group ->
        group.products?.apply { map { it.group = group.id } } ?: emptyList()
    }
}

/**
 * Model for Group from remote database & Room
 * */
@Fts4
@Entity
data class Group(
    val id: Int = 0,
    val name: String? = null
) {
    /** Only for remote database */
    @Ignore var products: List<Product>? = null
}

/**
 * Model for Product from remote database & Room
 * */
@Fts4
@Entity
data class Product(
    val id: Int = 0,
    var group: Int = 0,
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null,
    val inStart: Boolean = false,
    val inStart2: Boolean = false
)

/**
 * Model for Product in cart
 * */
@Fts4
@Entity
data class ProductInCart(
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null,
    val count: Int = 0
)