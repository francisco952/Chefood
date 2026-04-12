package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R

data class RecipeTag(
    val name: String,
    var isSelected: Boolean = false
)
class TagAdapter(
    private val tags: MutableList<RecipeTag>
) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnTag = view.findViewById<Button>(R.id.txtCategorie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categories, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tags[position]
        holder.btnTag.text = item.name
        if(item.isSelected){
            holder.btnTag.setBackgroundColor(holder.itemView.context.getColor(R.color.bg_select_tag))
            holder.btnTag.setTextColor(holder.itemView.context.getColor(R.color.white))
        }else{
            holder.btnTag.setBackgroundColor(holder.itemView.context.getColor(R.color.bg_tag))
            holder.btnTag.setTextColor(holder.itemView.context.getColor(R.color.calories))
        }
        holder.btnTag.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                val current = tags[pos]
                current.isSelected = !current.isSelected
                notifyItemChanged(pos)
            }
        }
    }
}