package com.alesat1215.productsfromerokhin.data.local

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Ignore
import androidx.room.PrimaryKey

/** Model for [Profile] data */
@Fts4
@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val rowid: Int = 0,
    val name: String = "",
    val phone: String = "",
    val address: String = ""
)