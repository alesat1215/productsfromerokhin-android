package com.alesat1215.productsfromerokhin.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.databinding.FragmentProfileBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * [ProfileFragment] subclass of [DaggerFragment].
 * Screen with delivery info
 */
class ProfileFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentProfileBinding.inflate(inflater, container, false).apply {
        viewModel = this@ProfileFragment.viewModel
    }.root

}