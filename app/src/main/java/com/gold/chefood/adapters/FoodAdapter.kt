package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.gold.chefood.Recipe
import com.squareup.picasso.Picasso

class FoodAdapter (private val recipes: List<Recipe>, val onClick: (Int) -> Unit,val onDelete: (Int) -> Unit): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val calories = view.findViewById<TextView>(R.id.txtCalories)
        val description = view.findViewById<TextView>(R.id.txtDescription)
        val image = view.findViewById<ImageView>(R.id.imgFood)
        val btn_view = view.findViewById<ImageButton>(R.id.viewFood)
        val btn_favorite = view.findViewById<ImageButton>(R.id.favoriteFood)
        val btn_delete = view.findViewById<ImageButton>(R.id.deleteFood)
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
        if(recipe.favorite){
            holder.btn_favorite.setImageResource(R.drawable.ic_bookmark_heart)
        }else{
            holder.btn_favorite.setImageResource(R.drawable.ic_favorite)
        }
        holder.btn_favorite.setOnClickListener {
            recipe.favorite = !recipe.favorite
            if(recipe.favorite){
                Toast.makeText(holder.itemView.context, "Se agrego a favoritos", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(holder.itemView.context, "Se elimino de favoritos", Toast.LENGTH_SHORT).show()
            }
            notifyItemChanged(position)
        }
        holder.btn_view.setOnClickListener {
            onClick(recipe.id)
        }
        holder.btn_delete.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Receta eliminada", Toast.LENGTH_SHORT).show()
            onDelete(position)
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