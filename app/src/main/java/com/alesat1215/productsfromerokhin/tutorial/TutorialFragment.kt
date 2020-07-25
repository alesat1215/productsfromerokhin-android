package com.alesat1215.productsfromerokhin.tutorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Instruction
import com.alesat1215.productsfromerokhin.databinding.FragmentTutorialBinding
import com.alesat1215.productsfromerokhin.util.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
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
    ) = FragmentTutorialBinding.inflate(inflater, container, false).apply {
        // Set adapter to pager
        pagerTutorial.adapter = ViewPagerAdapter<Instruction>(this@TutorialFragment) { InstructionFragment() }
        setDataToPagerAdapter()
        // Set tubs for pager
        TabLayoutMediator(tabsTutorial, pagerTutorial) { tab, position ->
            Log.d("Tutorial", "$position")
        }.attach()
    }.root

    private fun setDataToPagerAdapter() {
        viewModel.instructions().observe(viewLifecycleOwner, Observer {
            Log.d("Tutorial", "${it}")
            (pager_tutorial.adapter as? ViewPagerAdapter<Instruction>)?.apply {
                setData(it)
            }
        })
    }
}