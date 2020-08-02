package com.alesat1215.productsfromerokhin.load

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.tutorial.InstructionFragment
import com.orhanobut.logger.Logger
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

    /** Sign in to firebase auth & load data */
    private fun setupLoadingAnimation() {
        // Hide BottomNavigationView
        activity?.nav_view?.visibility = View.GONE
        // Sign in firebase
        viewModel.firebaseAuth().observe(viewLifecycleOwner, Observer {
            it.onSuccess { loadData() }
            it.onFailure { Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show() }
        })


    }
    /** When data is loading navigate to destination */
    private fun loadData() {
        if (tutorialIsRead()) {
            val loadPhoneAndTitles = Transformations.switchMap(viewModel.loadCompletePhone()) {
                if (it) {
                    Logger.d("Load phone complete")
                    return@switchMap viewModel.loadCompleteTitles()
                } else {
                    return@switchMap MutableLiveData(it)
                }
            }
            // Subscribe to trigger of phone, titles & products loading
            Transformations.switchMap(loadPhoneAndTitles) {
                if (it) {
                    Logger.d("Load titles complete")
                    return@switchMap viewModel.loadCompleteProducts()
                } else {
                    return@switchMap MutableLiveData(it)
                }
            }.observe(viewLifecycleOwner, Observer {
                if (it) {
                    // For non empty phone, titles & products navigate to destination
                    findNavController().navigate(R.id.action_loadFragment_to_startFragment)
                    Logger.d("Load products complete")
                }
            })
        } else {
            // Subscribe to trigger of tutorial loading
            viewModel.loadCompleteTutorial().observe(viewLifecycleOwner, Observer {
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
        (activity?.getSharedPreferences(InstructionFragment.SHARED_PREFS, MODE_PRIVATE)
            ?.getBoolean(InstructionFragment.IS_READ, false) ?: false).also {
            Logger.d("Tutorial is read: $it")
        }

}