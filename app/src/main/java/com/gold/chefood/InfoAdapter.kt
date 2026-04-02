package com.gold.chefood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

data class InfoItem(
    val title: String,
    val value: String,
    val color: Int,
    val bgColor: Int
)

class InfoAdapter(private val list: List<InfoItem>): RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.titleInfo)
        val value: TextView = view.findViewById(R.id.countInfo)
        val container: LinearLayout = view as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_info,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title
        holder.value.text = item.value

        holder.title.setTextColor(
            ContextCompat.getColor(holder.itemView.context, item.color)
        )
        holder.value.setTextColor(
            ContextCompat.getColor(holder.itemView.context, item.color)
        )
        holder.container.backgroundTintList =
            ContextCompat.getColorStateList(holder.itemView.context, item.bgColor)
    }
    override fun getItemCount() = list.size
}