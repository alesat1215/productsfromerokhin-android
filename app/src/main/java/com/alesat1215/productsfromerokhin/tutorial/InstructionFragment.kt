package com.alesat1215.productsfromerokhin.tutorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Instruction
import com.alesat1215.productsfromerokhin.databinding.FragmentInstructionBinding
import com.alesat1215.productsfromerokhin.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_instruction.*

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
        // Set text
        arguments?.takeIf { it.containsKey(ViewPagerAdapter.ITEM) }?.apply {
            textInstruction.text = getParcelable<Instruction>(ViewPagerAdapter.ITEM)?.text
        }
        // Set OK button visibility
        arguments?.takeIf { it.containsKey(ViewPagerAdapter.IS_LAST) }?.apply {
            val isLast = getBoolean(ViewPagerAdapter.IS_LAST)
            Log.d("Tutorial", "Last instruction: ${getBoolean(ViewPagerAdapter.IS_LAST)}")
            okInstruction.visibility = if (isLast) View.VISIBLE else View.INVISIBLE
        }
    }.root

    override fun onStart() {
        super.onStart()
        // Hide BottomNavigationView
        activity?.nav_view?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        // Show BottomNavigationView
        activity?.nav_view?.visibility = View.VISIBLE
    }
}