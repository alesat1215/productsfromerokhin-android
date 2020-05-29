package com.alesat1215.productsfromerokhin.data

data class Groups(
    var name: String? = null,
    var index: Int? = null,
    var img_path: String? = null,
    var products: List<Product>? = null
)

data class Product(
    var name: String? = null,
    var last_name: String? = null,
    var index: Int? = null,
    var price: Int? = null,
    var inCart: Int = 0
)