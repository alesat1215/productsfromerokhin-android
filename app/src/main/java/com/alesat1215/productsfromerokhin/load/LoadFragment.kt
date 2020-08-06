package com.alesat1215.productsfromerokhin.load

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
import com.alesat1215.productsfromerokhin.MainActivity
import com.alesat1215.productsfromerokhin.R
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
        if ((activity as? MainActivity)?.tutorialIsRead() == true) {
            val loadPhoneTitles = Transformations.switchMap(viewModel.loadCompletePhone()) {
                if (it) {
                    Logger.d("Load phone complete")
                    return@switchMap viewModel.loadCompleteTitles()
                } else {
                    return@switchMap MutableLiveData(it)
                }
            }

            val loadPhoneTitlesProducts= Transformations.switchMap(loadPhoneTitles) {
                if (it) {
                    Logger.d("Load titles complete")
                    return@switchMap viewModel.loadCompleteProducts()
                } else {
                    return@switchMap MutableLiveData(it)
                }
            }
            // Subscribe to trigger of phone, titles, products & about products loading
            Transformations.switchMap(loadPhoneTitlesProducts) {
                if (it) {
                    Logger.d("Load products complete")
                    return@switchMap viewModel.loadCompleteAboutProducts()
                } else {
                    return@switchMap MutableLiveData(it)
                }
            }.observe(viewLifecycleOwner, Observer {
                if (it) {
                    // For non empty phone, titles, products & about products navigate to destination
                    findNavController().navigate(R.id.action_loadFragment_to_startFragment)
                    Logger.d("Load about products complete")
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

}