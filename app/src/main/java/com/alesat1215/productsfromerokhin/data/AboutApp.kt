package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4

/** Model for [AboutApp] */
@Fts4
@Entity
data class AboutApp(
    val privacy: String = "",
    val versionCode: Int = 0,
    val googlePlay: String = ""
)