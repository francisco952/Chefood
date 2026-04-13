package com.gold.chefood

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VideoPlayerDialogFragment : DialogFragment() {

    private var isFavorite = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_video_player, null)

        val title = requireArguments().getString(ARG_TITLE).orEmpty()
        val description = requireArguments().getString(ARG_DESCRIPTION).orEmpty()
        val videoUrl = requireArguments().getString(ARG_VIDEO_URL).orEmpty()
        isFavorite = requireArguments().getBoolean(ARG_IS_FAVORITE, false)
        val recipeId = requireArguments().getInt(ARG_ID)

        val videoView = view.findViewById<VideoView>(R.id.videoView)
        val tvTitle = view.findViewById<TextView>(R.id.tvDialogVideoTitle)
        val tvDescription = view.findViewById<TextView>(R.id.tvDialogVideoDescription)
        val btnFavorite = view.findViewById<Button>(R.id.btnFavorite)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)
        val btnClose = view.findViewById<Button>(R.id.btnClose)

        tvTitle.text = title
        tvDescription.text = description

        val mediaController = android.widget.MediaController(context)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.parse(videoUrl))
        videoView.setOnPreparedListener { it.start() }
        btnFavorite.text = if (isFavorite) getString(R.string.video_unfavorite) else getString(R.string.video_favorite)

        btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            btnFavorite.text = if (isFavorite) {
                getString(R.string.video_unfavorite)
            } else {
                getString(R.string.video_favorite)
            }
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(KEY_ACTION to ACTION_FAVORITE, KEY_ID to recipeId, KEY_IS_FAVORITE to isFavorite)
            )
            Toast.makeText(
                context,
                if (isFavorite) "${title} agregado a favoritos" else "${title} removido de favoritos",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnDelete.setOnClickListener {
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(KEY_ACTION to ACTION_DELETE, KEY_ID to recipeId)
            )
            dismiss()
        }

        btnClose.setOnClickListener { dismiss() }

        return MaterialAlertDialogBuilder(context)
            .setView(view)
            .create()
    }

    override fun onStop() {
        super.onStop()
        dialog?.findViewById<VideoView>(R.id.videoView)?.stopPlayback()
    }

    companion object {
        const val REQUEST_KEY = "video_modal_action"
        const val KEY_ACTION = "action"
        const val KEY_ID = "id"
        const val KEY_IS_FAVORITE = "is_favorite"
        const val ACTION_DELETE = "delete"
        const val ACTION_FAVORITE = "favorite"

        private const val ARG_ID = "arg_id"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_DESCRIPTION = "arg_description"
        private const val ARG_VIDEO_URL = "arg_video_url"
        private const val ARG_IS_FAVORITE = "arg_is_favorite"

        fun newInstance(food: VideoFood): VideoPlayerDialogFragment {
            val fragment = VideoPlayerDialogFragment()
            fragment.arguments = bundleOf(
                ARG_ID to food.id,
                ARG_TITLE to food.title,
                ARG_DESCRIPTION to food.description,
                ARG_VIDEO_URL to food.videoUrl,
                ARG_IS_FAVORITE to food.isFavorite
            )
            return fragment
        }
    }
}
