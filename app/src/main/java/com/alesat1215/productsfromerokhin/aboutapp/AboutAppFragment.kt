package com.alesat1215.productsfromerokhin.aboutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.databinding.FragmentAboutAppBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * [AboutAppFragment] subclass of [DaggerFragment].
 * Screen with about app info
 */
class AboutAppFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AboutAppViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAboutAppBinding.inflate(inflater, container, false).apply {
        viewModel = this@AboutAppFragment.viewModel
        lifecycleOwner = this@AboutAppFragment
        executePendingBindings()
    }.root
}