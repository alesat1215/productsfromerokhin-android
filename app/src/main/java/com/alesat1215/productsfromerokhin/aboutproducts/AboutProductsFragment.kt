package com.alesat1215.productsfromerokhin.aboutproducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alesat1215.productsfromerokhin.R
import dagger.android.support.DaggerFragment

/**
 * [AboutProductsFragment] subclass of [DaggerFragment].
 * Screen with about products info
 */
class AboutProductsFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_products, container, false)
    }

}