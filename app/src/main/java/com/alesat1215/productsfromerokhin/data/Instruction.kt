package com.alesat1215.productsfromerokhin.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Fts4
import kotlinx.android.parcel.Parcelize

/** Model for [Instruction] */
@Parcelize
@Fts4
@Entity
data class Instruction(
    val title: String = "",
    val text: String = "",
    val img_path: String = ""
) : Parcelable