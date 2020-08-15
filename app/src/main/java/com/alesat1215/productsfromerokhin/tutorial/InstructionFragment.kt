package com.alesat1215.productsfromerokhin.tutorial

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alesat1215.productsfromerokhin.MainActivity
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Instruction
import com.alesat1215.productsfromerokhin.databinding.FragmentInstructionBinding
import com.alesat1215.productsfromerokhin.util.ViewPagerAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

/**
 * [InstructionFragment] subclass of [Fragment].
 * Screen with instruction
 */
class InstructionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentInstructionBinding.inflate(inflater, container, false).apply {
        // Set variables
        fragment = this@InstructionFragment
        instruction = arguments?.takeIf { it.containsKey(ViewPagerAdapter.ITEM) }
            ?.getParcelable(ViewPagerAdapter.ITEM) ?: Instruction()
        isLast = arguments?.takeIf { it.containsKey(ViewPagerAdapter.IS_LAST) }
            ?.getBoolean(ViewPagerAdapter.IS_LAST, false) ?: false
        executePendingBindings()
    }.root

    /** Save finish status to shared prefs & navigate to destination */
    fun finishRead() =
        // Set finish status to shared prefs
        activity?.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            ?.edit()?.putBoolean(IS_READ, true)?.apply()?.also {
                Logger.d("Tutorial read")
                // Navigate to destination
                findNavController().navigateUp()
            }

    override fun onStart() {
        super.onStart()
        // Hide BottomNavigationView
        activity?.nav_view?.visibility = View.GONE
    }

    companion object {
        const val SHARED_PREFS = "com.alesat1215.productsfromerokhin"
        const val IS_READ = "IS_READ"
    }

}