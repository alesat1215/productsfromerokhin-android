package com.alesat1215.productsfromerokhin.more

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alesat1215.productsfromerokhin.databinding.FragmentMoreBinding
import com.orhanobut.logger.Logger

/**
 * [MoreFragment] subclass of [Fragment].
 * Screen with navigation to more screens
 */
class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMoreBinding.inflate(inflater, container, false).apply {
        fragment = this@MoreFragment
    }.root

    fun share() {
        // Create intent
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${activity?.packageName}")
            type = "text/plain"
        }
        // Show chooser
        val chooser: Intent = Intent.createChooser(intent, "")
        activity?.packageManager?.also {
            intent.resolveActivity(it)?.also {
                startActivity(chooser)
                Logger.d("Select messenger for share")
            }
        }
    }
}