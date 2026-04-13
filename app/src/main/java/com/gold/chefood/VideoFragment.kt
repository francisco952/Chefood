package com.gold.chefood

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class VideoFragment : Fragment(R.layout.fragment_video) {

    private lateinit var adapter: VideoFoodAdapter
    private lateinit var repository: RecipeRepository
    private val foods = mutableListOf<VideoFood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(
            VideoPlayerDialogFragment.REQUEST_KEY,
            this
        ) { _, result ->
            val action = result.getString(VideoPlayerDialogFragment.KEY_ACTION)
            val id = result.getInt(VideoPlayerDialogFragment.KEY_ID)

            if (action == VideoPlayerDialogFragment.ACTION_DELETE) {
                adapter.removeById(id)
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.setDeleted(id, true)
                }
                Toast.makeText(requireContext(), "Receta eliminada", Toast.LENGTH_SHORT).show()
            }

            if (action == VideoPlayerDialogFragment.ACTION_FAVORITE) {
                val isFavorite = result.getBoolean(VideoPlayerDialogFragment.KEY_IS_FAVORITE)
                adapter.updateFavorite(id, isFavorite)
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.setFavorite(id, isFavorite)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvVideoFoods)
        repository = RecipeRepository(requireContext())

        adapter = VideoFoodAdapter(foods) { food ->
            VideoPlayerDialogFragment
                .newInstance(food)
                .show(childFragmentManager, "video-player")
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            foods.clear()
            foods.addAll(repository.getVideoFoods())
            adapter.notifyDataSetChanged()
        }
    }
}