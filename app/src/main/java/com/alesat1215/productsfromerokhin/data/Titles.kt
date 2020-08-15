package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4

/** Model for [Titles] */
@Fts4
@Entity
data class Titles(
    val title: String = "",
    val img: String = "",
    val imgTitle: String = "",
    val productsTitle: String = "",
    val productsTitle2: String = ""
)