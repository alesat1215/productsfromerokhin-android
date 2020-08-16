package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.TypeConverter
import androidx.room.TypeConverters

/** Model for [OrderWarning] */
@Fts4
@Entity
@TypeConverters(OrderWarningGroupsConverter::class)
data class OrderWarning(
    val text: String = "",
    val groups: List<String> = emptyList()
)

class OrderWarningGroupsConverter {
    @TypeConverter
    fun fromGroups(groups: List<String>) = groups.joinToString(",")
    @TypeConverter
    fun toGroups(string: String) = string.split(",")
}