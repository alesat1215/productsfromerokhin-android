package com.alesat1215.productsfromerokhin.data

import androidx.room.*

data class Products(
    var groups: List<Group>? = null
) {
    fun productsWithGroupOrder(): List<Product>? = groups?.flatMap { group ->
        group.products?.apply { map { it.groupOrder = group.order } } ?: emptyList()
    }
}

data class ProductsDB(
    @Embedded val group: Group,
    @Relation(
        parentColumn = "order",
        entityColumn = "groupOrder"
    )
    var products: List<Product>
)

@Fts4
@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid")
    var id: Int = 0,
    var order: Int = 0,
    var name: String? = null,
    @Ignore var products: List<Product>? = null
)

@Fts4
@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid")
    var id: Int = 0,
    var order: Int = 0,
    var groupOrder: Int = 0,
    var name: String? = null,
    var consist: String? = null,
    var img: String? = null,
    var price: Int? = null,
    var inStart: Boolean = false,
    var inStart2: Boolean = false
)