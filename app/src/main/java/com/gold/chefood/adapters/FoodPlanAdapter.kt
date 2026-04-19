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

data class FoodPlan(
    val name:String,
    val calories:String,
    val carbohydrates:String,
    val protein:String,
    val totalfat:String,
    val image_url:String
)
class FoodPlanAdapter(
    private val list: List<Recipe>,
    private val showButton: Boolean = true,
    private val onClick: ((Recipe) -> Unit)? = null
) : RecyclerView.Adapter<FoodPlanAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName = view.findViewById<TextView>(R.id.titlePlan)
        val txtCalories = view.findViewById<TextView>(R.id.caloriePlan)
        val txtProtein = view.findViewById<TextView>(R.id.proteinPlan)
        val txtCarbo = view.findViewById<TextView>(R.id.carboPlan)
        val txtGrass = view.findViewById<TextView>(R.id.grassPlan)
        val imgFood = view.findViewById<ImageView>(R.id.imgPlan)
        val btnChange = view.findViewById<ImageButton>(R.id.btnChange)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plan, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.txtName.text = item.name
        holder.txtCalories.text = "Calorias: ${item.calories}"
        holder.txtProtein.text = "Proteina: ${item.protein}"
        holder.txtCarbo.text = "Carbohidratos: ${item.carbohydrates}"
        holder.txtGrass.text = "Grasas: ${item.totalfat}"
        holder.btnChange.visibility = if(showButton) View.VISIBLE else View.GONE
        Picasso.get()
            .load(item.image_url)
            .fit()
            .centerCrop()
            .error(R.drawable.fondo_menu)
            .into(holder.imgFood)
        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
    }
    override fun getItemCount() = list.size
}