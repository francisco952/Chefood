package com.gold.chefood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gold.chefood.WebApi.RetrofitClient
import com.gold.chefood.adapters.NewsAdapter
import com.gold.chefood.models.NewsResponse
import com.gold.chefood.models.SearchRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WebFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recycler: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private val apiKey = "dffd159163776a073984f225e14485cfaad7facb"


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
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        progressBar = view.findViewById<ProgressBar>(R.id.progressCardWeb)
        recycler = view.findViewById(R.id.recycleWeb)
        searchView = view.findViewById(R.id.searchWeb)

        recycler.layoutManager = LinearLayoutManager(requireContext())

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()){
                    showLoading()
                    searchNotice(query.trim())
                }
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchNotice("recetas de comida")
        showLoading()
        return view
    }

    private fun searchNotice(text:String){
        val request = SearchRequest(text)
        RetrofitClient.instance.searchNews(apiKey, request)
            .enqueue(object : Callback<NewsResponse>{
                override fun onResponse( call: Call<NewsResponse>, response: Response<NewsResponse> ){
                    println("CODEAPI: ${response.code()}")
                    if(response.isSuccessful && response.body() != null){
                        val list = response.body()!!.images
                        recycler.adapter = NewsAdapter(list){item ->
                            val fragment = BrowserFragment()
                            fragment.arguments = Bundle().apply {
                                putString("url",item.link)
                            }
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.drawer_layout, fragment)
                                .addToBackStack(null)
                                .commit()
                        }
                        hideLoading()
                    }else{
                        hideLoading()
                        println(response.errorBody()?.string())
                    }
                }
                override fun onFailure( call: Call<NewsResponse>, t: Throwable ){
                    hideLoading()
                    t.printStackTrace()
                }
            })
    }
    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recycler.visibility = View.GONE
    }
    private fun hideLoading() {
        progressBar.visibility = View.GONE
        recycler.visibility = View.VISIBLE
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WebFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}