package com.alesat1215.productsfromerokhin.tutorial

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alesat1215.productsfromerokhin.MainActivity
import com.alesat1215.productsfromerokhin.MainActivity.Companion.IS_READ
import com.alesat1215.productsfromerokhin.MainActivity.Companion.SHARED_PREFS
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
        fragment = this@InstructionFragment
        // Set text
        arguments?.takeIf { it.containsKey(ViewPagerAdapter.ITEM) }?.apply {
            val instruction = getParcelable<Instruction>(ViewPagerAdapter.ITEM)
            titleInstruction.text = instruction?.title
            textInstruction.text = instruction?.text
        }
        // Set OK button visibility
        arguments?.takeIf { it.containsKey(ViewPagerAdapter.IS_LAST) }?.apply {
            val isLast = getBoolean(ViewPagerAdapter.IS_LAST)
            Logger.d("Last instruction: ${getBoolean(ViewPagerAdapter.IS_LAST)}")
            okInstruction.visibility = if (isLast) View.VISIBLE else View.INVISIBLE
        }
        executePendingBindings()
    }.root

    /** Save finish status to shared prefs & navigate to destination */
    fun finishRead() {
        // Set finish status to shared prefs
        (activity as? MainActivity)?.finishRead()
        Logger.d("Tutorial read")
        // Navigate to destination
        findNavController().navigate(R.id.action_tutorialFragment_to_loadFragment)
    }

    override fun onStart() {
        super.onStart()
        // Hide BottomNavigationView
        activity?.nav_view?.visibility = View.GONE
    }

}