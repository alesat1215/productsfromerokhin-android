package com.alesat1215.productsfromerokhin.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentStartBinding
import com.alesat1215.productsfromerokhin.databinding.ListItemBinding

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : Fragment() {

    val viewModel: StartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentStartBinding.inflate(inflater, container, false).apply {
            viewModel = this@StartFragment.viewModel
            adapterToList(list) { (it.price ?: 0) == 250 }
            adapterToList(list2)
            executePendingBindings()
//            viewModel.products { (it.price ?: 0) == 250 }
//                .observe(viewLifecycleOwner, Observer {
//                    list.adapter = MyAdapter(it)
//                })
//            viewModel.products()
//                .observe(viewLifecycleOwner, Observer {
//                    list2.adapter = MyAdapter(it)
//                })
        }.root
    }

    private fun adapterToList(list: RecyclerView, predicate: ((Product) -> Boolean)? = null) =
        viewModel.products(predicate).observe(viewLifecycleOwner, Observer {
            list.adapter = MyAdapter(it)
        })

}

class MyViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(private val dataSet: List<Product>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val product = dataset.get(position)
//        holder.itemView.name.text = product.name
//        holder.itemView.price.text = product.price.toString() + " P/kg"
//        holder.itemView.productImage.setImageResource(androidx.vectordrawable.R.color.notification_action_color_filter)
//        holder.itemView.count.text = "3"
        holder.binding.setVariable(BR.product, dataSet[position])
        holder.binding.executePendingBindings()
    }
}
