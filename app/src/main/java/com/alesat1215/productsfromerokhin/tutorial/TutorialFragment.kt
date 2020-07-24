package com.alesat1215.productsfromerokhin.tutorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * [TutorialFragment] subclass of [DaggerFragment].
 * Screen with order tutorial
 */
class TutorialFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    /** Need "by activity" for save state */
    private val viewModel by activityViewModels<TutorialViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkRemoteConfig()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    private fun checkRemoteConfig() {
        viewModel.instructions().observe(viewLifecycleOwner, Observer {
            Log.d("Tutorial", "${it}")
        })
    }
}