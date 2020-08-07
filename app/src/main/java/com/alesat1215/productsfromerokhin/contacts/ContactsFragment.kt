package com.alesat1215.productsfromerokhin.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.MainActivity
import com.alesat1215.productsfromerokhin.databinding.FragmentContactsBinding
import com.orhanobut.logger.Logger
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
        fragment = this@ContactsFragment
        viewModel = this@ContactsFragment.viewModel
        lifecycleOwner = this@ContactsFragment
        executePendingBindings()
    }.root

    fun call() {
        val phone = viewModel.contacts.value?.phone ?: return
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phone}")
        }
        // Show chooser
        val chooser: Intent = Intent.createChooser(intent, "")
        activity?.packageManager?.also {
            intent.resolveActivity(it)?.also {
                startActivity(chooser)
                Logger.d("Select call")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as? MainActivity)?.setupBackButton(true)
    }

    override fun onStop() {
        super.onStop()
        (activity as? MainActivity)?.setupBackButton(false)
    }

}