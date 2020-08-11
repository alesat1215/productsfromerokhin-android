package com.alesat1215.productsfromerokhin.load

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.tutorial.InstructionFragment.Companion.IS_READ
import com.alesat1215.productsfromerokhin.tutorial.InstructionFragment.Companion.SHARED_PREFS
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerFragment
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
        // Start load data
        firebaseAuth()
    }

    /** Sign in to firebase auth & load data */
    private fun firebaseAuth() {
        // Sign in firebase
        viewModel.firebaseAuth().observe(viewLifecycleOwner, Observer {
            it.onSuccess { loadData() }
            it.onFailure { Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show() }
        })
    }
    /** When data is loading navigate to destination */
    private fun loadData() {
        if (tutorialIsRead()) {
            viewModel.loadDataComplete().observe(viewLifecycleOwner, Observer {
                if (it) {
                    // For non empty data navigate to destination
                    findNavController().navigate(R.id.action_loadFragment_to_startFragment)
                    Logger.d("Load data complete")
                }
            })
        } else {
            // Subscribe to trigger of tutorial loading
            viewModel.loadTutorialComplete().observe(viewLifecycleOwner, Observer {
                if (it) {
                    // For non empty tutorials navigate to destination
                    findNavController().navigate(R.id.action_loadFragment_to_tutorialFragment)
                    Logger.d("Load tutorial complete")
                }
            })
        }
    }

    /** Check tutorial read */
    private fun tutorialIsRead() =
        activity?.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            ?.getBoolean(IS_READ, false).also {
                Logger.d("Tutorial is read: $it")
            } ?: false

}