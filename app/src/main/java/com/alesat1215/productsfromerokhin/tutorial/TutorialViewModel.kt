package com.alesat1215.productsfromerokhin.tutorial

import androidx.lifecycle.ViewModel
import com.alesat1215.productsfromerokhin.data.Instruction
import com.alesat1215.productsfromerokhin.data.Tutorial

class TutorialViewModel: ViewModel() {
    var step = 0
    private val tutorial = Tutorial(instructions = listOf(
        Instruction("text 1"),
        Instruction("text 2"),
        Instruction("text 3")
    ))
}