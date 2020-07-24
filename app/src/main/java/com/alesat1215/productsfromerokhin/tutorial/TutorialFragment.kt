package com.alesat1215.productsfromerokhin.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alesat1215.productsfromerokhin.R

/**
 * A simple [Fragment] subclass.
 * Use the [TutorialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }
}