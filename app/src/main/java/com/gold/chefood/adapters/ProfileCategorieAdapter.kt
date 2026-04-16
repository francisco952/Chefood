package com.gold.chefood.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.gold.chefood.adapters.InfoAdapter.ViewHolder

data class ProfileCategorieItem(
    val icon: Int,
    val value: String,
    val color: Int
)
private var showAll = false
class ProfileCategorieAdapter(private val list: List<ProfileCategorieItem>): RecyclerView.Adapter<ProfileCategorieAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val icon: ImageView = view.findViewById(R.id.profileIcon)
        val value: TextView = view.findViewById(R.id.profileCategorie)
        val container: LinearLayout = view as LinearLayout
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileCategorieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categories_profile,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileCategorieAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.value.text = item.value
        holder.icon.setImageResource(item.icon)
        val color = ContextCompat.getColor(holder.itemView.context, item.color)
        val bgColor = lightenColor(color)
        val background = holder.icon.background as GradientDrawable
        background.setColor(bgColor)
        holder.icon.setColorFilter(color)
    }
    override fun getItemCount(): Int {
        return if (showAll) list.size else minOf(3, list.size)
    }
    fun toggleShowAll() {
        showAll = !showAll
        notifyDataSetChanged()
    }

    fun lightenColor(color: Int): Int {
        val factor = 0.85f
        val r = (Color.red(color) + (255 - Color.red(color)) * factor).toInt()
        val g = (Color.green(color) + (255 - Color.green(color)) * factor).toInt()
        val b = (Color.blue(color) + (255 - Color.blue(color)) * factor).toInt()
        return Color.rgb(r, g, b)
    }
}