package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.gold.chefood.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gold.chefood.models.Article

class NewsAdapter(private val list: List<Article>, private val onClick:(Article)->Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.findViewById<CardView>(R.id.cardNew)
        val img = view.findViewById<ImageView>(R.id.imgNews)
        val title = view.findViewById<TextView>(R.id.txtTitleNews)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.card.setOnClickListener {
            onClick(item)
        }

        holder.title.text = item.title
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.fondo_menu)
            .into(holder.img)
    }
}