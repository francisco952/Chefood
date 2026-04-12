package com.gold.chefood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.google.android.material.textfield.TextInputEditText

data class Ingredient(
    var count: String = "",
    var name: String = ""
)
class AddIngredientAdapter(
    private val ingredients: MutableList<Ingredient>,
    private val onDelete: (Int) -> Unit
): RecyclerView.Adapter<AddIngredientAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cantidad = view.findViewById<TextInputEditText>(R.id.etCantidad)
        val ingredient = view.findViewById<TextInputEditText>(R.id.etIngredient)
        val btnDelete = view.findViewById<ImageButton>(R.id.deleteItemIngredient)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_form_ingredients, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ingredients[position]

        holder.cantidad.setText(item.count)
        holder.ingredient.setText(item.name)


        holder.cantidad.addTextChangedListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                ingredients[pos].count = it.toString()
            }
        }

        holder.ingredient.addTextChangedListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                ingredients[pos].name = it.toString()
            }
        }

        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }

    }
}