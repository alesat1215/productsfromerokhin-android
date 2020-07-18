package com.alesat1215.productsfromerokhin.data.local

import androidx.room.*

/** Model for [Product] with list of [ProductInCart] */
data class Product(
    @Embedded val productDB: ProductDB? = null,
    @Relation(
        parentColumn = "name",
        entityColumn = "name"
    )
    val inCart: List<ProductInCart>
) {
    /** @return [ProductInCart] from [ProductDB] */
    fun productInCart() = ProductInCart(name = productDB?.name, consist = productDB?.consist, img = productDB?.img, price = productDB?.price)//, count)
}

/** Model for [ProductDB] */
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

/** Model for [GroupDB] */
@Fts4
@Entity
data class GroupDB(
    val name: String? = null
)

/** Model for [ProductInCart] in cart */
@Fts4
@Entity
data class ProductInCart(
    @PrimaryKey(autoGenerate = true)
    var rowid: Int = 0,
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null
)

/** Model for [Titles] */
@Fts4
@Entity
data class Titles(
    val title: String? = null,
    val img: String? = null,
    val imgTitle: String? = null,
    val productsTitle: String? = null,
    val productsTitle2: String? = null
)