package com.alesat1215.productsfromerokhin.data

import androidx.room.*

/** Model for [ProductInfo] with list of [ProductInCart] */
data class ProductInfo(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "name",
        entityColumn = "name"
    )
    val inCart: List<ProductInCart>
) {
    /** @return [ProductInCart] from [Product] */
    fun asProductInCart() =
        ProductInCart(name = product.name)
    /** @return total sum of price for products in cart */
    fun priceSumInCart() = (product.price) * inCart.count()
    /** @return text for order with: name | price * count = sum | */
    fun textForOrder() =
        "${product.name} ${product.price} * ${inCart.count()} = ${priceSumInCart()}"
}

/** Model for [Product] */
@Fts4
@Entity
data class Product(
    var group: String = "",
    val name: String = "",
    val consist: String = "",
    val img: String = "",
    val price: Int = 0,
    val inStart: Boolean = false,
    val inStart2: Boolean = false
)

/** Model for [Group] with list of products */
@Fts4
@Entity
data class Group(
    val name: String = ""
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
    val name: String = ""
)