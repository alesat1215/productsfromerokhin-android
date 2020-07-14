package com.alesat1215.productsfromerokhin.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Relation

/**
 * Model for Product with count in cart
 * */
//data class Product(
//    var group: String? = null,
//    val name: String? = null,
//    val consist: String? = null,
//    val img: String? = null,
//    val price: Int? = null,
//    val inStart: Boolean = false,
//    val inStart2: Boolean = false//,
////    val count: Int = 0
//) {
//    fun productInCart() = ProductInCart(name, consist, img, price)//, count)
//}

data class Product(
    @Embedded val productDB: ProductDB? = null,
    @Relation(
        parentColumn = "name",
        entityColumn = "name"
    )
    val inCart: List<ProductInCart>
) {
    fun productInCart() = ProductInCart(productDB?.name, productDB?.consist, productDB?.img, productDB?.price)//, count)
}

/**
 * Model for Product
 * */
@Fts4
@Entity
data class ProductDB(
    var group: String? = null,
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null,
    val inStart: Boolean = false,
    val inStart2: Boolean = false
)

/**
 * Model for Group
 * */
@Fts4
@Entity
data class GroupDB(
    val name: String? = null
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
    val price: Int? = null//,
//    val count: Int = 0
)

/**
 * Model for Titles
 * */
@Fts4
@Entity
data class Titles(
    val title: String? = null,
    val img: String? = null,
    val imgTitle: String? = null,
    val productsTitle: String? = null,
    val productsTitle2: String? = null
)