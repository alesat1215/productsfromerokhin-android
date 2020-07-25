package com.alesat1215.productsfromerokhin.load

import android.content.Context.MODE_PRIVATE
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
import com.alesat1215.productsfromerokhin.tutorial.InstructionFragment
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
    ) = inflater.inflate(R.layout.fragment_load, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoadingAnimation()
    }

    /** When data is loading navigate to destination */
    private fun setupLoadingAnimation() {
        // Hide BottomNavigationView
        activity?.nav_view?.visibility = View.GONE

        if (tutorialIsRead()) {
            // Subscribe to trigger of products loading
            viewModel.loadCompleteProducts().observe(viewLifecycleOwner, Observer {
                if (it) {
                    // For non empty products navigate to destination
                    findNavController().navigate(R.id.action_loadFragment_to_startFragment)
                    Log.d("Load", "Load products complete")
                }
            })
        } else {
            // Subscribe to trigger of tutorial loading
            viewModel.loadCompleteTutorial().observe(viewLifecycleOwner, Observer {
                if (it) {
                    // For non empty tutorials navigate to destination
                    findNavController().navigate(R.id.action_loadFragment_to_tutorialFragment)
                    Log.d("Load", "Load tutorial complete")
                }
            })
        }
    }
    /** Check tutorial read */
    private fun tutorialIsRead() =
        (activity?.getSharedPreferences(InstructionFragment.SHARED_PREFS, MODE_PRIVATE)
            ?.getBoolean(InstructionFragment.IS_READ, false) ?: false).also {
            Log.d("Load", "Tutorial is read: $it")
        }

}