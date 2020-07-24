package com.alesat1215.productsfromerokhin.data.local

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

/** Model for [Profile] data */
@Fts4
@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true)
    var rowid: Int = 0,
    val name: String = "",
    val phone: String = "",
    val address: String = ""
) {
    /** @return delivery info for message */
    fun delivery(): String {
        // Return empty string for empty profile
        if (name.isEmpty() and phone.isEmpty() and address.isEmpty()) return ""
        // Build delivery info from not empty profile
        val separator = System.lineSeparator()
        var result = ""
        result += separator + separator + name
        result += (if (result.last().toString() != separator && phone.isNotEmpty()) separator else "") + phone
        result += (if (result.last().toString() != separator && address.isNotEmpty()) separator else "") + address
        return result
    }
}