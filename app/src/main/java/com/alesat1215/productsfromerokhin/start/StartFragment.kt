package com.alesat1215.productsfromerokhin.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentStartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : Fragment() {

    val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentStartBinding.inflate(inflater, container, false).apply {
            viewModel = this@StartFragment.viewModel
            adapterToList(list) { (it.price ?: 0) == 250 }
            adapterToList(list2)
            executePendingBindings()
        }.root

    private fun adapterToList(list: RecyclerView, predicate: ((Product) -> Boolean)? = null) =
        viewModel.products(predicate).observe(viewLifecycleOwner, Observer {
            list.swapAdapter(BindRVAdapter(it, R.layout.list_item), false)
        })

}


