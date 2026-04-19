package com.gold.chefood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gold.chefood.modal.ModalPlanFragment
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var breakfastRecipe: Recipe? = null
    private var lunchRecipe: Recipe? = null
    private var dinnerRecipe: Recipe? = null
    private var snackRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnBreakfast = view.findViewById<MaterialButton>(R.id.addBreakfast)
        val btnLunch = view.findViewById<MaterialButton>(R.id.addLunch)
        val btnDinner = view.findViewById<MaterialButton>(R.id.addDinner)
        val btnSnack = view.findViewById<MaterialButton>(R.id.addSnack)
        val bntSave = view.findViewById<MaterialButton>(R.id.savePlan)
        val btnCancel = view.findViewById<MaterialButton>(R.id.cancelPlan)

        btnBreakfast.setOnClickListener { openModal("Breakfast") }
        btnLunch.setOnClickListener { openModal("Lunch") }
        btnDinner.setOnClickListener { openModal("Dinner") }
        btnSnack.setOnClickListener { openModal("Snack") }
    }
    private fun openModal(type: String) {
        ModalPlanFragment.newInstance(type) { recipe ->
            updateContainer(type, recipe)
        }.show(parentFragmentManager, "foodModal")
    }

    private fun updateContainer(type: String, recipe: Recipe){
        when(type){
            "Breakfast" -> breakfastRecipe = recipe
            "Lunch" -> lunchRecipe = recipe
            "Dinner" -> dinnerRecipe = recipe
            "Snack" -> snackRecipe = recipe
        }
        updateTotals()
        val container = when (type) {
            "Breakfast" ->
                view?.findViewById<LinearLayout>(R.id.contentBreakfast)
            "Lunch" ->
                view?.findViewById<LinearLayout>(R.id.contentLunch)
            "Dinner" ->
                view?.findViewById<LinearLayout>(R.id.contentDinner)
            else ->
                view?.findViewById<LinearLayout>(R.id.contentSnack)
        }
        container?.removeAllViews()
        container?.setBackgroundResource(android.R.color.transparent)
        val card = layoutInflater.inflate(
            R.layout.item_plan,
            container,
            false
        )
        val title = card.findViewById<TextView>(R.id.titlePlan)
        val calories = card.findViewById<TextView>(R.id.caloriePlan)
        val protein = card.findViewById<TextView>(R.id.proteinPlan)
        val carbo = card.findViewById<TextView>(R.id.carboPlan)
        val grass = card.findViewById<TextView>(R.id.grassPlan)
        val image = card.findViewById<ImageView>(R.id.imgPlan)
        val btnChange = card.findViewById<ImageButton>(R.id.btnChange)

        title.text = recipe.name
        calories.text = "Calorías: ${recipe.calories}"
        protein.text = "Proteina: ${recipe.protein}"
        carbo.text = "Carbohidratos: ${recipe.carbohydrates}"
        grass.text = "Grasas: ${recipe.totalfat}"
        Picasso.get()
            .load(recipe.image_url)
            .fit()
            .centerCrop()
            .into(image)
        btnChange.setOnClickListener {
            openModal(type)
        }
        container?.addView(card)
    }
    private fun updateTotals(){
        val totalCalories = getInfoRecipe(breakfastRecipe) { it.calories } + getInfoRecipe(lunchRecipe) { it.calories } +
                getInfoRecipe(dinnerRecipe) { it.calories } + getInfoRecipe(snackRecipe) { it.calories }
        val totalProtein = getInfoRecipe(breakfastRecipe) { it.protein } + getInfoRecipe(lunchRecipe) { it.protein } +
                getInfoRecipe(dinnerRecipe) { it.protein } + getInfoRecipe(snackRecipe) { it.protein }
        val totalCarbo = getInfoRecipe(breakfastRecipe) { it.carbohydrates } + getInfoRecipe(lunchRecipe) { it.carbohydrates } +
                getInfoRecipe(dinnerRecipe) { it.carbohydrates } + getInfoRecipe(snackRecipe) { it.carbohydrates }
        val totalFat = getInfoRecipe(breakfastRecipe) { it.totalfat } + getInfoRecipe(lunchRecipe) { it.totalfat } +
                getInfoRecipe(dinnerRecipe) { it.totalfat } + getInfoRecipe(snackRecipe) { it.totalfat }
        view?.findViewById<TextView>(R.id.totalCalorie)?.text = "$totalCalories kcal"
        view?.findViewById<TextView>(R.id.totalProtein)?.text = "$totalProtein g"
        view?.findViewById<TextView>(R.id.totalCarbo)?.text = "$totalCarbo g"
        view?.findViewById<TextView>(R.id.totalGrass)?.text = "$totalFat g"
    }
    private fun getInfoRecipe(recipe: Recipe?, selector: (Recipe) -> String?): Int {
        if(recipe == null) return 0

        return selector(recipe)
            ?.replace("g", "")
            ?.replace("kcal", "")
            ?.trim()
            ?.toIntOrNull() ?: 0
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}