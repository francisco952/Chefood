package com.gold.chefood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WebResultAdapter(
    private val items: MutableList<WebResultUi>,
    private val onResultClick: (WebResultUi) -> Unit
) : RecyclerView.Adapter<WebResultAdapter.WebResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_web_result, parent, false)
        return WebResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebResultViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onResultClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun submitData(newItems: List<WebResultUi>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class WebResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvResultTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvResultDescription)
        private val tvUrl: TextView = itemView.findViewById(R.id.tvResultUrl)
        private val tvBrowser: TextView = itemView.findViewById(R.id.tvResultBrowser)

        fun bind(item: WebResultUi) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            tvUrl.text = item.url
            tvBrowser.text = item.browserLabel
        }
    }
}

data class WebResultUi(
    val title: String,
    val description: String,
    val url: String,
    val browserLabel: String
)
