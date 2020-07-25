package com.alesat1215.productsfromerokhin.tutorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Instruction
import com.alesat1215.productsfromerokhin.util.ViewPagerAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_instruction.*
import kotlinx.android.synthetic.main.fragment_tutorial.*
import javax.inject.Inject

/**
 * [TutorialFragment] subclass of [DaggerFragment].
 * Screen with order tutorial
 */
class TutorialFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    /** Need "by activity" for save state */
    private val viewModel by activityViewModels<TutorialViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager_tutorial.adapter = ViewPagerAdapter<Instruction>(this) { InstructionFragment() }
        setDataToPagerAdapter()
    }

    private fun setDataToPagerAdapter() {
        viewModel.instructions().observe(viewLifecycleOwner, Observer {
            Log.d("Tutorial", "${it}")
            (pager_tutorial.adapter as? ViewPagerAdapter<Instruction>)?.apply {
                setData(it)
            }
        })
    }
}

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
    }
}