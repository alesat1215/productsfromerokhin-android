package com.alesat1215.productsfromerokhin.data

import androidx.room.*

data class RemoteData(
    val title: String? = null,
    val imageTitle: String? = null,
    val listTitle: String? = null,
    val listTitle2: String? = null,
    val groups: List<Group>? = null
) {
    fun productsWithGroupOrder(): List<Product>? = groups?.flatMap { group ->
        group.products?.apply { map { it.groupOrder = group.order } } ?: emptyList()
    }
}

data class Products(
    @Embedded val group: Group,
    @Relation(
        parentColumn = "order",
        entityColumn = "groupOrder"
    )
    val products: List<Product>
)

@Fts4
@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid")
    var id: Int = 0,
    val order: Int = 0,
    val name: String? = null,
    @Ignore var products: List<Product>? = null
)

@Fts4
@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid")
    var id: Int = 0,
    val order: Int = 0,
    var groupOrder: Int = 0,
    val name: String? = null,
    val consist: String? = null,
    val img: String? = null,
    val price: Int? = null,
    val inStart: Boolean = false,
    val inStart2: Boolean = false
)