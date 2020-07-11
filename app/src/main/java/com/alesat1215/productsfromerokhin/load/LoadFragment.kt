package com.alesat1215.productsfromerokhin.load

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alesat1215.productsfromerokhin.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * A [LoadFragment] subclass of [DaggerFragment].
 * Loading screen
 */
class LoadFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LoadViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoadingAnimation()
    }

    /** When data is loading navigate to destination */
    private fun setupLoadingAnimation() {
        // Hide BottomNavigationView
        activity?.nav_view?.visibility = View.GONE
        // Subscribe to trigger of data loading
        viewModel.loadComplete().observe(viewLifecycleOwner, Observer {
            if (it) {
                // For non empty data navigate to destination
                findNavController().navigate(R.id.action_loadFragment_to_startFragment)
                // Show BottomNavigationView
                activity?.nav_view?.visibility = View.VISIBLE
                Log.d("Load", "Load complete")
            }
        })
    }

}