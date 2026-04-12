package com.gold.chefood.modal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.R
import com.gold.chefood.Recipe
import com.gold.chefood.adapters.InfoAdapter
import com.gold.chefood.adapters.InfoItem
import com.gold.chefood.adapters.IngredientItem
import com.gold.chefood.adapters.IngredientsAdapter
import com.gold.chefood.adapters.StepAdapter
import com.gold.chefood.adapters.StepItem
import com.gold.chefood.getRecipes
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ModalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModalFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modal, container, false)
    }
    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawableResource(android.R.color.white)

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Close modal
        val btn_close = view.findViewById<ImageButton>(R.id.closeModal)
        btn_close.setOnClickListener {
            dismiss()
        }
        //
        val recipe = arguments?.getSerializable("recipe") as? Recipe ?: return
        //Img
        val image = view.findViewById<ImageView>(R.id.imgModal)
        Picasso.get()
            .load(recipe?.image_url)
            .into(image)
        //create title
        val title = view.findViewById<TextView>(R.id.headerModal)
        title.text = recipe?.name
        //create description
        val description = view.findViewById<TextView>(R.id.txt_description)
        description.text = recipe?.description
        //create info
        val recycler = view.findViewById<RecyclerView>(R.id.contentInfo)
        val list = listOf(
            InfoItem("CALORIAS", "${recipe?.calories}", R.color.calories, R.color.bg_calories),
            InfoItem("PROTEINA", "${recipe?.protein}", R.color.protein, R.color.bg_protein),
            InfoItem("CARBO", "${recipe?.carbohydrates}", R.color.carbo, R.color.bg_carbo),
            InfoItem("GRASAS", "${recipe?.totalfat}", R.color.grass, R.color.bg_grass)
        )
        recycler.layoutManager = GridLayoutManager(requireContext(),4)
        recycler.adapter = InfoAdapter(list)
        //create ingredients
        val recyclerIngredients = view.findViewById<RecyclerView>(R.id.contentIngredients)
        val listIngredients = recipe?.ingredients?.map {
            IngredientItem(it)
        } ?: emptyList()
        recyclerIngredients.layoutManager = LinearLayoutManager(requireContext())
        recyclerIngredients.adapter = IngredientsAdapter(listIngredients)
        recyclerIngredients.isNestedScrollingEnabled = false
        //create steps
        val recyclerSteps = view.findViewById<RecyclerView>(R.id.contentSteps)
        val listSteps = recipe?.steps?.map {
            StepItem(it.step, it.value, "")
        } ?: emptyList()
        recyclerSteps.layoutManager = LinearLayoutManager(requireContext())
        recyclerSteps.adapter = StepAdapter(listSteps)
        recyclerSteps.isNestedScrollingEnabled = false

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ModalFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}