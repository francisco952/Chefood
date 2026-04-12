package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.google.android.material.textfield.TextInputEditText

data class RecipeStep (
    var step: String = "",
    var value: String = ""
)
class AddStepAdapter(
    private val steps: MutableList<RecipeStep >,
    private val onDelete: (Int) -> Unit
): RecyclerView.Adapter<AddStepAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val stepnumber = view.findViewById<TextView>(R.id.numberAddStep)
        val stepname = view.findViewById<TextInputEditText>(R.id.etStep)
        val btnDelete = view.findViewById<ImageButton>(R.id.deleteItemStep)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_form_step, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = steps.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = steps[position]

        holder.stepnumber.text = (position + 1).toString()
        holder.stepname.setText(null)

        holder.stepname.setText(item.value)

        holder.stepname.addTextChangedListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                steps[pos].value = it.toString()
            }
        }


        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }

    }
}