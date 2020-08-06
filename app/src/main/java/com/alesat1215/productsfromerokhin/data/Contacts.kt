package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4

/** Model for [Contacts] */
@Fts4
@Entity
data class Contacts(
    val phone: String = "",
    val address: String = ""
)