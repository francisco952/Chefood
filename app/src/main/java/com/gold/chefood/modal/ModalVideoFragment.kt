package com.gold.chefood.modal

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gold.chefood.R
import com.gold.chefood.Recipe


class ModalVideoFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modal_video, container, false)
        val title = view.findViewById<TextView>(R.id.modalTitleVideo)
        val description = view.findViewById<TextView>(R.id.modalDescriptionVideo)
        val webView = view.findViewById<WebView>(R.id.webMovie)
        val progress = view.findViewById<ProgressBar>(R.id.progressVideo)

        val recipe = arguments?.getSerializable("recipe") as Recipe

        title.text = recipe.name
        description.text = recipe.description

        progress.visibility = View.VISIBLE
        webView.visibility = View.INVISIBLE

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.mediaPlaybackRequiresUserGesture = false

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress.visibility = View.VISIBLE
                webView.visibility = View.INVISIBLE
                super.onPageStarted(view, url, favicon)
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                progress.visibility = View.GONE
                webView.visibility = View.VISIBLE
                super.onPageFinished(view, url)
            }
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }
        }
        webView.loadUrl(recipe.video)

        return view
    }
    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}