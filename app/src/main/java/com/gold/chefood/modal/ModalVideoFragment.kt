package com.gold.chefood.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gold.chefood.R
import com.gold.chefood.Recipe

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ModalVideoFragment : DialogFragment() {
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
        return inflater.inflate(R.layout.fragment_modal_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe = arguments?.getSerializable("recipe") as? Recipe ?: return
        val title = view.findViewById<TextView>(R.id.modalTitleVideo)
        val description = view.findViewById<TextView>(R.id.modalDescriptionVideo)

        title.text = recipe.name
        description.text = recipe.description

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModalVideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}