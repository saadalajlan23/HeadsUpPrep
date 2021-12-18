package com.example.headsupprep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.*


class RecyclerViewAdapter(private var celebrites: List<Celebrity>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder (itemView:View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = celebrites[position]

        holder.itemView.apply {

            tvName.text = user.name
            tvTaboo1.text = user.taboo1
            tvTaboo2.text = user.taboo2
            tvTaboo3.text = user.taboo3


        }
    }

    override fun getItemCount() = celebrites.size
    fun update(students: List<Celebrity>){
        this.celebrites = students
        notifyDataSetChanged()
    }
}
