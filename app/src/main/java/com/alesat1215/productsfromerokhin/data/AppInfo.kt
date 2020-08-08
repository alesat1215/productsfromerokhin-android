package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4

/** Model for [AppInfo] */
@Fts4
@Entity
data class AppInfo(
    val privacy: String = "",
    val versionCode: Int = 0
)