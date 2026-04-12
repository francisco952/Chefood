package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.gold.chefood.Recipe
import com.squareup.picasso.Picasso

class FoodAdapter (private val recipes: List<Recipe>, val onClick: (Int) -> Unit): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val calories = view.findViewById<TextView>(R.id.txtCalories)
        val description = view.findViewById<TextView>(R.id.txtDescription)
        val image = view.findViewById<ImageView>(R.id.imgFood)
        val btn_view = view.findViewById<ImageButton>(R.id.viewFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.title.text = recipe.name
        holder.calories.text = recipe.calories
        holder.description.text = recipe.description
        holder.btn_view.setOnClickListener {
            onClick(recipe.id)
        }
        Picasso.get()
            .load(recipe.image_url)
            .fit()
            .centerCrop()
            .error(R.drawable.fondo_menu)
            .into(holder.image)
    }

    override fun getItemCount() = recipes.size
}