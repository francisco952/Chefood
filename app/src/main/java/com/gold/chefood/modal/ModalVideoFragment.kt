package com.gold.chefood.modal

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.gold.chefood.R
import com.gold.chefood.Recipe


class ModalVideoFragment : DialogFragment() {
    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modal_video, container, false)
        val title = view.findViewById<TextView>(R.id.modalTitleVideo)
        val description = view.findViewById<TextView>(R.id.modalDescriptionVideo)
        playerView = view.findViewById(R.id.webMovie)

        val recipe = arguments?.getSerializable("recipe") as Recipe

        title.text = recipe.name
        description.text = recipe.description

        player = ExoPlayer.Builder(requireContext()).build()
        playerView.player = player

        val uri = Uri.parse("android.resource://${requireContext().packageName}/${R.raw.spageti_pesto}")
        val mediaItem = MediaItem.fromUri(uri)

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}