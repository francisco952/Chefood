package com.gold.chefood

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        setStyle(STYLE_NORMAL,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Close modal
        val btn_close = view.findViewById<ImageButton>(R.id.closeModal)
        btn_close.setOnClickListener {
            dismiss()
        }
        val position = arguments?.getInt("position")
        Log.d("MODAL_DEBUG","Posición:$position")
        //create info
        val recycler = view.findViewById<RecyclerView>(R.id.contentInfo)
        val list = listOf(
            InfoItem("CALORIAS", "250", R.color.calories, R.color.bg_calories),
            InfoItem("PROTEINA", "10g", R.color.protein, R.color.bg_protein),
            InfoItem("CARBO", "20g", R.color.carbo, R.color.bg_carbo),
            InfoItem("GRASAS", "5g", R.color.grass, R.color.bg_grass)
        )
        recycler.layoutManager = GridLayoutManager(requireContext(),4)
        recycler.adapter = InfoAdapter(list)
        //create ingredients
        val recyclerIngredients = view.findViewById<RecyclerView>(R.id.contentIngredients)
        val listIngredients = listOf(
            IngredientItem("Arroz"),
            IngredientItem("Pollo"),
            IngredientItem("Sal"),
            IngredientItem("Aceite")
        )
        recyclerIngredients.layoutManager = LinearLayoutManager(requireContext())
        recyclerIngredients.adapter = IngredientsAdapter(listIngredients)
        recyclerIngredients.isNestedScrollingEnabled = false
        //create steps
        val recyclerSteps = view.findViewById<RecyclerView>(R.id.contentSteps)
        val listSteps = listOf(
            StepItem("1","Prender estifa","Temperatura ambiente en 85°C"),
            StepItem("2","Cortar Pollo","Cortar pollo en porciones pequeñas")
        )
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