package com.gold.chefood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class VideoFoodAdapter(
    private val foods: MutableList<VideoFood>,
    private val onVideoClick: (VideoFood) -> Unit
) : RecyclerView.Adapter<VideoFoodAdapter.VideoFoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoFoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_food, parent, false)
        return VideoFoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoFoodViewHolder, position: Int) {
        val item = foods[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onVideoClick(item) }
    }

    override fun getItemCount(): Int = foods.size

    fun removeById(id: Int) {
        val index = foods.indexOfFirst { it.id == id }
        if (index >= 0) {
            foods.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateFavorite(id: Int, isFavorite: Boolean) {
        val index = foods.indexOfFirst { it.id == id }
        if (index >= 0) {
            foods[index] = foods[index].copy(isFavorite = isFavorite)
            notifyItemChanged(index)
        }
    }

    class VideoFoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivThumb: ImageView = itemView.findViewById(R.id.ivVideoThumb)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvVideoItemTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvVideoItemDescription)
        private val tvCalories: TextView = itemView.findViewById(R.id.tvVideoCalories)

        fun bind(item: VideoFood) {
            ivThumb.load(item.thumbnailUrl)
            tvTitle.text = item.title
            tvDescription.text = item.description
            tvCalories.text = itemView.context.getString(R.string.kcal_format, item.calories)
        }
    }
}
