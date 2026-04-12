package com.gold.chefood

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.adapters.AddIngredientAdapter
import com.gold.chefood.adapters.AddStepAdapter
import com.gold.chefood.adapters.Ingredient
import com.gold.chefood.adapters.RecipeStep
import com.gold.chefood.adapters.RecipeTag
import com.gold.chefood.adapters.TagAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
val tagList = mutableListOf(
    RecipeTag("Vegetariano"),
    RecipeTag("Saludable"),
    RecipeTag("Rico en Carbohidratos"),
    RecipeTag("Orgánico"),
    RecipeTag("Sin gluten"),
    RecipeTag("Sin azúcar"),
    RecipeTag("Vegano"),
    RecipeTag("Rico en proteina")
)
/**
 * A simple [Fragment] subclass.
 * Use the [AddFoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFoodFragment : DialogFragment() {
    private lateinit var ingredient_Adapter: AddIngredientAdapter
    private lateinit var step_Adapter: AddStepAdapter
    private val ingredientList = mutableListOf<Ingredient>()
    private val stepList = mutableListOf<RecipeStep>()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //close
        val btn_close = view.findViewById<ImageView>(R.id.closeAddModal)
        btn_close.setOnClickListener {
            dismiss()
        }
        //ingredients
        val recyclerIngredients = view.findViewById<RecyclerView>(R.id.recycleaddIngredient)
        val btnAddIngredients = view.findViewById<MaterialButton>(R.id.addIngredient)
        //step
        val recyclerSteps = view.findViewById<RecyclerView>(R.id.recycleaddStep)
        val btnAddStep = view.findViewById<MaterialButton>(R.id.addStep)
        //tag
        val recyclerTags = view.findViewById<RecyclerView>(R.id.recycleAddTag)
        val tagAdapter = TagAdapter(tagList)
        recyclerTags.adapter = tagAdapter

        ingredient_Adapter = AddIngredientAdapter(ingredientList) { position ->
            ingredientList.removeAt(position)
            ingredient_Adapter.notifyItemRemoved(position)
        }

        step_Adapter = AddStepAdapter(stepList) { position ->
            stepList.removeAt(position)
            step_Adapter.notifyItemRemoved(position)
            step_Adapter.notifyItemRangeChanged(position, stepList.size)
        }

        recyclerIngredients.layoutManager = LinearLayoutManager(requireContext())
        recyclerIngredients.adapter = ingredient_Adapter

        recyclerSteps.layoutManager = LinearLayoutManager(requireContext())
        recyclerSteps.adapter = step_Adapter

        ingredientList.add(Ingredient())
        ingredient_Adapter.notifyItemInserted(0)

        stepList.add(RecipeStep())
        step_Adapter.notifyItemInserted(0)

        val flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
        }
        recyclerTags.layoutManager = flexboxLayoutManager
        recyclerTags.adapter = tagAdapter

        btnAddIngredients.setOnClickListener {
            ingredientList.add(Ingredient())
            ingredient_Adapter.notifyItemInserted(ingredientList.size - 1)
        }

        btnAddStep.setOnClickListener {
            stepList.add(RecipeStep())
            step_Adapter.notifyItemInserted(stepList.size - 1)
        }
        //form add food
        val dataFood = getRecipes(requireContext())
        val idFood = (dataFood.maxOfOrNull { it.id } ?: 0) + 1
        val etTitle = view.findViewById<TextInputEditText>(R.id.etTitle)
        val etDescription = view.findViewById<TextInputEditText>(R.id.etDescription)
        val etCalorie = view.findViewById<TextInputEditText>(R.id.etCalories)
        val etProtein = view.findViewById<TextInputEditText>(R.id.etProtein)
        val etCarbo = view.findViewById<TextInputEditText>(R.id.etCarbo)
        val etTotalgrass = view.findViewById<TextInputEditText>(R.id.etGrass)
        val btnSave = view.findViewById<Button>(R.id.newFood)
        btnSave.setOnClickListener {
            try{
                val selectedTypes = tagList
                    .filter { it.isSelected }
                    .map { it.name }
                val stepsFormatted = stepList.mapIndexed { index, step ->
                    Step(
                        step = (index + 1).toString(),
                        value = step.value.trim()
                    )
                }.filter { it.value.isNotEmpty() }
                val ingredientsFormatted = ingredientList
                    .map {
                        "${it.count} ${it.name}".trim()
                    }
                    .filter { it.isNotEmpty() }
                val recipevalue = Recipe(
                    id = idFood,
                    name = etTitle.text.toString(),
                    favorite = false,
                    image_url = "https://img.freepik.com/foto-gratis/vista-arriba-mesa-llena-comida_23-2149209253.jpg?semt=ais_hybrid&w=740&q=80",
                    description = etDescription.text.toString(),
                    calories = etCalorie.text.toString(),
                    carbohydrates = etCarbo.text.toString(),
                    protein = etProtein.text.toString(),
                    totalfat = etTotalgrass.text.toString(),
                    type = selectedTypes,
                    ingredients = ingredientsFormatted,
                    steps = stepsFormatted
                )
                parentFragmentManager.setFragmentResult(
                    "recipe",
                    Bundle().apply {
                        putString("recipe", Gson().toJson(recipevalue))
                    }
                )
                dismiss()
                val prettyJson = GsonBuilder().setPrettyPrinting().create().toJson(recipevalue)

            }catch (e: Exception){
                Log.e("ERROR_JSON", "Exception: ${e.message}", e)
            }
        }
    }
    fun saveFood(newFood:Recipe){
        val file = File(requireContext().filesDir, "recipe.json")
        val gson = Gson()
        val recipeList: MutableList<Recipe>
        if (file.exists()) {
            val json = file.readText()

            val type = object : TypeToken<RecipeResponse>() {}.type
            val response: RecipeResponse = gson.fromJson(json, type)

            recipeList = response.recipes.toMutableList()
        } else {
            recipeList = mutableListOf()
        }
        //add new food
        recipeList.add(newFood)
        val updatedJson = gson.toJson(RecipeResponse(recipeList))
        file.writeText(updatedJson)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFoodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}