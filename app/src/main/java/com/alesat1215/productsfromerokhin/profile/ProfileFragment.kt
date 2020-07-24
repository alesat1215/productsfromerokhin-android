package com.alesat1215.productsfromerokhin.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.databinding.FragmentProfileBinding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
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
        fragment = this@ProfileFragment
        lifecycleOwner = this@ProfileFragment
        executePendingBindings()
    }.root

    fun save() {
        viewModel.updateProfile(
            profile_name.text.toString(),
            profile_phone.text.toString(),
            profile_address.text.toString()
        )
        view?.clearFocus()
    }

}