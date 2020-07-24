package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4
import com.alesat1215.productsfromerokhin.data.local.GroupDB

data class Tutorial(
    val instructions: List<Instruction> = emptyList()
)

/** Model for [Instruction] */
@Fts4
@Entity
data class Instruction(
    val text: String = "",
    val img_path: String = ""
)