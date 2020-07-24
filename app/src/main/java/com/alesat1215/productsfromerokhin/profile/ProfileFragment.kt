package com.alesat1215.productsfromerokhin.profile

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
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
        // Show alert in center about saving
        Toast.makeText(context, R.string.profile_saved, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }.show()

        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()
        // For hide keyboard when navigate to destination
        hideKeyboard()
    }

    private fun hideKeyboard() {
        view?.also {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                ?.hideSoftInputFromWindow(it.windowToken, 0)
            Log.d("Profile", "Hide keyboard")
        }
    }

}