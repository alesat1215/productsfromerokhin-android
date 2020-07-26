package com.alesat1215.productsfromerokhin.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alesat1215.productsfromerokhin.databinding.FragmentMoreBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * [MoreFragment] subclass of [Fragment].
 * Screen with navigation to more screens
 */
class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMoreBinding.inflate(inflater, container, false).root

    override fun onStart() {
        super.onStart()
        // Show BottomNavigationView
        activity?.nav_view?.visibility = View.VISIBLE
    }
}