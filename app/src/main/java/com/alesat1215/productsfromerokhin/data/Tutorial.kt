package com.alesat1215.productsfromerokhin.data

data class Tutorial(
    val instructions: List<Instruction> = emptyList()
)

data class Instruction(
    val text: String = "",
    val img_path: String = ""
)