package com.gold.chefood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class StepItem(
    val number: String,
    val title: String,
    val name: String
)

class StepAdapter(private val list: List<StepItem>): RecyclerView.Adapter<StepAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val number: TextView = view.findViewById(R.id.numberStep)
        val title : TextView = view.findViewById(R.id.titleStep)
        val name : TextView = view.findViewById(R.id.textStep)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_step,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: StepAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.number.text = item.number
        holder.title.text = item.title
        holder.name.text = item.name
    }
    override fun getItemCount() = list.size
}