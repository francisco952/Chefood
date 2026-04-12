package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R

data class IngredientItem(
    val name: String
)
class IngredientsAdapter(private val list: List<IngredientItem>): RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val value: TextView = view.findViewById(R.id.txt_ingredient)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.value.text = item.name
    }
    override fun getItemCount() = list.size
}