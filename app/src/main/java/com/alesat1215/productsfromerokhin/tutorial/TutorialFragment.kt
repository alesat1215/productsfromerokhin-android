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
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Instruction
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
//        setAdapterToPager(pager_tutorial)
        pager_tutorial.adapter = TutorialAdapter(this)
        setDataToPagerAdapter()
    }

    private fun setDataToPagerAdapter() {
        viewModel.instructions().observe(viewLifecycleOwner, Observer {
            Log.d("Tutorial", "${it}")
//            pager.adapter = TutorialAdapter(this, it)
            (pager_tutorial.adapter as? TutorialAdapter)?.apply {
                instructions = it
                notifyDataSetChanged()
            }
        })
    }
}

class TutorialAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var instructions: List<Instruction> = emptyList()

    override fun getItemCount() = instructions.count()

    override fun createFragment(position: Int): Fragment {
         return InstructionFragment().apply {
            arguments = Bundle().apply { putParcelable(SERIALIZABLE_KEY, instructions[position]) }
        }
    }

}

private const val SERIALIZABLE_KEY = "SERIALIZABLE_KEY"

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
        arguments?.takeIf { it.containsKey(SERIALIZABLE_KEY) }?.apply {
            text_instruction.text = getParcelable<Instruction>(SERIALIZABLE_KEY)?.text
        }
    }
}