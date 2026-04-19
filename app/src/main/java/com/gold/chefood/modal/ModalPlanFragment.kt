package com.gold.chefood.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.gold.chefood.Recipe
import com.gold.chefood.adapters.FoodPlanAdapter
import com.gold.chefood.getRecipes

private lateinit var adapter: FoodPlanAdapter
private val recipeList = mutableListOf<Recipe>()
class ModalPlanFragment( private val onSelect: (Recipe) -> Unit): DialogFragment() {
    private var foodTag: String? = null
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodTag = arguments?.getString("type")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modal_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.recycleListPlan)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        val allRecipes = getRecipes(requireContext())
        val filterType = when(foodTag){
            "Breakfast" -> "saludable"
            "Lunch" -> "Rico en proteina"
            "Dinner" -> "sin gluten"
            else -> "Rico en Carbohidratos"
        }
        val recipeList = allRecipes.filter { recipe ->
            recipe.type.any { type ->
                type.equals(filterType, ignoreCase = true)
            }
        }.toMutableList()
        adapter = FoodPlanAdapter(recipeList,false){
            recipe -> onSelect(recipe)
            dismiss()
        }
        recycler.adapter = adapter
    }

    companion object {
        fun newInstance(type:String, onSelect: (Recipe) -> Unit): ModalPlanFragment{
            val fragment = ModalPlanFragment(onSelect)
            val args = Bundle()
            args.putString("type",type)
            fragment.arguments = args
            return  fragment
        }
    }
}