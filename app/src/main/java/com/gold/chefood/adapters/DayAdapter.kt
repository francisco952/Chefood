package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R

class DayAdapter(private val list: List<DayItem>): RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtDay = view.findViewById<TextView>(R.id.txtDay)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_days_calendar, parent, false)
        val width = parent.measuredWidth / 5
            view.layoutParams.width = width
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.txtDay.text = item.dayName
        holder.txtDate.text = item.dayNumber.toString()
        if(item.isToday){
            holder.txtDate.setBackgroundResource(R.drawable.bg_today)
        }else{
            holder.txtDate.setBackgroundResource(R.drawable.bg_calendar)
        }
    }

    override fun getItemCount() = list.size
}