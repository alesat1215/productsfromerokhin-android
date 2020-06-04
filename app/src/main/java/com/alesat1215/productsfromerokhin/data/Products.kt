package com.alesat1215.productsfromerokhin.data

import androidx.room.*

@Fts4
@Entity
data class RemoteData(
    val title: String? = null,
    val imageTitle: String? = null,
    val listTitle: String? = null,
    val listTitle2: String? = null
) {
    @Ignore var groups: List<Group>? = null

    fun productsWithGroupId(): List<Product>? = groups?.flatMap { group ->
        group.products?.apply { map { it.group = group.id } } ?: emptyList()
    }
}

//data class Products(
//    @Embedded val group: Group,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "group"
//    )
//    val products: List<Product>
//)

@Fts4
@Entity
data class Group(
    val id: Int = 0,
    val name: String? = null
) {
    @Ignore var products: List<Product>? = null
}

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