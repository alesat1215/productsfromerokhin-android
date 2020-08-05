package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4

/** Model for [AboutProducts] */
@Fts4
@Entity
data class AboutProducts(
    val title: String = "",
    val text: String = "",
    val img: String = ""
)