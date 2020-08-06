package com.alesat1215.productsfromerokhin.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.databinding.FragmentContactsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * [ContactsFragment] subclass of [DaggerFragment].
 * Screen with contacts
 */
class ContactsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ContactsViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentContactsBinding.inflate(inflater, container, false).apply {
        viewModel = this@ContactsFragment.viewModel
        lifecycleOwner = this@ContactsFragment
        executePendingBindings()
    }.root

}