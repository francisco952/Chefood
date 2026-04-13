package com.gold.chefood

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class WebFragment : Fragment(R.layout.fragment_web) {

    private lateinit var adapter: WebResultAdapter
    private lateinit var emptyView: TextView
    private lateinit var repository: RecipeRepository
    private lateinit var searchEditText: TextInputEditText
    private val resources = mutableListOf<WebFoodResource>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvWebResults)
        searchEditText = view.findViewById(R.id.etSearch)
        emptyView = view.findViewById(R.id.tvWebEmpty)
        repository = RecipeRepository(requireContext())

        adapter = WebResultAdapter(mutableListOf()) { result ->
            val intent = Intent(requireContext(), WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.EXTRA_URL, result.url)
                putExtra(WebViewActivity.EXTRA_TITLE, result.title)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilter(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        viewLifecycleOwner.lifecycleScope.launch {
            resources.clear()
            resources.addAll(repository.getWebResources())
            applyFilter(searchEditText.text?.toString().orEmpty())
        }
    }

    private fun applyFilter(query: String) {
        val cleaned = query.trim().lowercase()
        val filtered = resources.filter { item ->
            if (cleaned.isEmpty()) {
                true
            } else {
                item.title.lowercase().contains(cleaned) ||
                    item.description.lowercase().contains(cleaned) ||
                    item.keywords.any { keyword -> keyword.lowercase().contains(cleaned) }
            }
        }

        val uiItems = filtered.map { item ->
            WebResultUi(
                title = item.title,
                description = item.description,
                url = item.url,
                browserLabel = getBrowserLabel(item.url)
            )
        }

        adapter.submitData(uiItems)
        emptyView.visibility = if (uiItems.isEmpty()) View.VISIBLE else View.GONE
    }

    @Suppress("DEPRECATION")
    private fun getBrowserLabel(url: String): String {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val packageManager = requireContext().packageManager
        val handlers = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        if (handlers.isEmpty()) {
            return getString(R.string.available_browser_none)
        }

        val browserNames = handlers
            .map { it.loadLabel(packageManager).toString() }
            .distinct()
            .take(3)
            .joinToString(", ")

        return getString(R.string.available_browser, browserNames)
    }
}