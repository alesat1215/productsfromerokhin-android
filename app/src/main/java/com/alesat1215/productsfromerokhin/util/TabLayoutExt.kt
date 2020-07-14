package com.alesat1215.productsfromerokhin.util

import com.google.android.material.tabs.TabLayout

fun TabLayout.tabWithText(text: CharSequence?): TabLayout.Tab? {
    for (i in 0 until tabCount) {
        val tab = getTabAt(i)
        if (tab?.text == text) return tab
    }
    return null
}