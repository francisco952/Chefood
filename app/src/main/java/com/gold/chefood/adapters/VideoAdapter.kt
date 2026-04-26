package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.gold.chefood.Recipe
import com.squareup.picasso.Picasso

class VideoAdapter(private val recipes: List<Recipe>, val onClick: (Int) -> Unit): RecyclerView.Adapter<VideoAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.imgVideo)
        val title = view.findViewById<TextView>(R.id.txtVideoTitle)
        val description = view.findViewById<TextView>(R.id.txtVideoDescription)
        val calories = view.findViewById<TextView>(R.id.txtVideoCalorie)
        val btn_Video = view.findViewById<ImageView>(R.id.modalVideo)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int){
        val recipe = recipes[position]
        holder.title.text = recipe.name
        holder.calories.text = recipe.calories
        holder.description.text = recipe.description

        holder.btn_Video.setOnClickListener {
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