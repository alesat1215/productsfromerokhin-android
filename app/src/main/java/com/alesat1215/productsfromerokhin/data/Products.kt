package com.alesat1215.productsfromerokhin.data

import androidx.room.*

data class Products(
    @Embedded val group: Group,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    var products: List<Product>? = null
)

@Fts4
@Entity
data class Group(
//    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid")
    var id: String = "",
    var index: Int? = null,
    var name: String? = null
)

@Fts4
@Entity
data class Product(
//    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid")
    var id: String = "",
    var groupId: String = "",
    var index: Int? = null,
    var name: String? = null,
    var last_name: String? = null,
    var img_path: String? = null,
    var price: Int? = null,
    var inCart: Int = 0,
    var inStartList: Boolean = false,
    var inStartList2: Boolean = false
)