package com.gold.chefood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter (val onClick: (Int) -> Unit): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val calories = view.findViewById<TextView>(R.id.txtCalories)
        val description = view.findViewById<TextView>(R.id.txtDescription)
        val btn_view = itemView.findViewById<ImageButton>(R.id.viewFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btn_view.setOnClickListener {
            onClick(position)
        }
        when(position) {
            0 -> {
                holder.title.text = "Hamburguesa"
                holder.calories.text = "500 CAL"
                holder.description.text = "Deliciosa hamburguesa"
            }
            1 -> {
                holder.title.text = "Pizza"
                holder.calories.text = "700 CAL"
                holder.description.text = "Pizza con queso"
            }
            2 -> {
                holder.title.text = "Ensalada"
                holder.calories.text = "200 CAL"
                holder.description.text = "Saludable"
            }
            3 -> {
                holder.title.text = "Pasta"
                holder.calories.text = "450 CAL"
                holder.description.text = "Con salsa"
            }
            4 -> {
                holder.title.text = "Sushi"
                holder.calories.text = "300 CAL"
                holder.description.text = "Fresco"
            }
        }
    }

    override fun getItemCount() = 5


}