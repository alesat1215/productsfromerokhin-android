package com.alesat1215.productsfromerokhin.data.local

import androidx.room.*

/** Model for [ProductInfo] with list of [ProductInCart] */
data class ProductInfo(
    @Embedded val product: Product? = null,
    @Relation(
        parentColumn = "name",
        entityColumn = "name"
    )
    val inCart: List<ProductInCart>
) {
    /** @return [ProductInCart] from [Product] */
    fun asProductInCart() = ProductInCart(name = product?.name)
    /** @return total sum of price for products in cart */
    fun priceSumInCart() = (product?.price ?: 0) * inCart.count()
    /** @return text for order with: name | price * count = sum | */
    fun textForOrder() =
        "${product?.name.orEmpty()} | ${product?.price ?: 0} * ${inCart.count()} = ${priceSumInCart()} |"
}

/** Model for [Product] */
@Fts4
@Entity
data class Product(
    var group: String? = null,
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null,
    val inStart: Boolean = false,
    val inStart2: Boolean = false
)

/** Model for [Group] with list of products */
@Fts4
@Entity
data class Group(
    val name: String? = null
) {
    @Ignore var products: List<Product> = emptyList()

    fun productsWithGroup(): List<Product> {
        products.forEach { it.group = name }
        return products
    }
}
/** @return products with group name */
fun products(groups: List<Group>) = groups.map { it.productsWithGroup() }.flatten()

/** Model for [ProductInCart] in cart */
@Fts4
@Entity
data class ProductInCart(
    @PrimaryKey(autoGenerate = true)
    var rowid: Int = 0,
    val name: String? = null
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