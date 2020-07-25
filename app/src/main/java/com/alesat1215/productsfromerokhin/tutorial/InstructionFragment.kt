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
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_instruction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(ViewPagerAdapter.ITEM) }?.apply {
            text_instruction.text = getParcelable<Instruction>(ViewPagerAdapter.ITEM)?.text
        }
        arguments?.takeIf { it.containsKey(ViewPagerAdapter.IS_LAST) }?.apply {
            val isLast = getBoolean(ViewPagerAdapter.IS_LAST)
            Log.d("Tutorial", "Last instruction: ${getBoolean(ViewPagerAdapter.IS_LAST)}")
            ok_instruction.visibility = if (isLast) View.VISIBLE else View.INVISIBLE
        }
        ok_instruction.setOnClickListener {
            findNavController().navigate(R.id.action_tutorialFragment_to_startFragment)
        }
    }

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