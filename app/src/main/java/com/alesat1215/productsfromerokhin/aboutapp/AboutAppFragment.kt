package com.alesat1215.productsfromerokhin.aboutapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.databinding.FragmentAboutAppBinding
import com.orhanobut.logger.Logger
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
        fragment = this@AboutAppFragment
        viewModel = this@AboutAppFragment.viewModel
        lifecycleOwner = this@AboutAppFragment
        executePendingBindings()
    }.root

    /** Show url with privacy policy */
    fun privacy() {
        // Create intent
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.aboutApp.value?.privacy.orEmpty()))
        // Show chooser
        val chooser: Intent = Intent.createChooser(intent, "")
        activity?.packageManager?.also {
            intent.resolveActivity(it)?.also {
                startActivity(chooser)
                Logger.d("Select browser for privacy policy")
            }
        }
    }
}