package com.alesat1215.productsfromerokhin.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alesat1215.productsfromerokhin.R
import kotlinx.android.synthetic.main.fragment_start.view.*
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false).apply {
            list.adapter = MyAdapter()
            list2.adapter = MyAdapter()
        }
    }

}

data class Products(val name: String, val price: Int)

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {

    val products = Array(10) { Products("Name name", 250) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = products[position]
        holder.itemView.name.text = product.name
        holder.itemView.price.text = product.price.toString() + " P/kg"
        holder.itemView.productImage.setImageResource(androidx.vectordrawable.R.color.notification_action_color_filter)
        holder.itemView.count.text = "3"
    }
}
