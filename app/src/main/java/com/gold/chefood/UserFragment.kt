package com.gold.chefood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.adapters.ProfileCategorieAdapter
import com.gold.chefood.adapters.ProfileCategorieItem
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnShow = view.findViewById<ImageButton>(R.id.btnShowmore)
        //List categories
        val recycleCategories = view.findViewById<RecyclerView>(R.id.recycleInfoCateogire)
        val listCategorie =  listOf(
            ProfileCategorieItem(R.drawable.ic_eco, "Vegetariano", R.color.tag_veget),
            ProfileCategorieItem(R.drawable.ic_avocado, "Saludable", R.color.tag_healt),
            ProfileCategorieItem(R.drawable.ic_bakery, "Rico en carbohidratos", R.color.tag_carbo),
            ProfileCategorieItem(R.drawable.ic_grass, "Orgánico", R.color.tag_organic),
            ProfileCategorieItem(R.drawable.ic_no_food, "Sin gluten", R.color.tag_gluten),
            ProfileCategorieItem(R.drawable.ic_cookie, "Sin azúcar", R.color.tag_sweet),
            ProfileCategorieItem(R.drawable.ic_spa, "Vegano", R.color.tag_vegano),
            ProfileCategorieItem(R.drawable.ic_fitness, "Rico en proteina", R.color.tag_protein)
        )
        val adapter = ProfileCategorieAdapter(listCategorie)
        recycleCategories.layoutManager = LinearLayoutManager(requireContext())
        recycleCategories.adapter = adapter
        btnShow.setOnClickListener {
            adapter.toggleShowAll()
            val expanded = btnShow.contentDescription == getString(R.string.show_more)
            if (expanded) {
                btnShow.contentDescription = "Ver menos"
                btnShow.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_visibility_off)
                )
            } else {
                btnShow.contentDescription = "Ver más"
                btnShow.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_visibility)
                )
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}