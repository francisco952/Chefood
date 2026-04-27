package com.gold.chefood

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class BrowserFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_browser, container, false)
        val webView = view.findViewById<WebView>(R.id.browserWeb)
        val progress = view.findViewById<ProgressBar>(R.id.progressWeb)
        val btn_close = view.findViewById<ImageView>(R.id.closeBrowser)
        val url = arguments?.getString("url") ?: ""

        progress.visibility = View.VISIBLE
        webView.setBackgroundColor(android.graphics.Color.WHITE)
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = false
        webView.settings.allowContentAccess = false

        webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress.visibility = View.VISIBLE
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                progress.visibility = View.GONE
            }
            override fun onPageCommitVisible(view: WebView?, url: String?) {
                progress.visibility = View.GONE
            }
        }


        if (!url.isNullOrEmpty()) {
            webView.loadUrl(url)
        }

        btn_close.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BrowserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}