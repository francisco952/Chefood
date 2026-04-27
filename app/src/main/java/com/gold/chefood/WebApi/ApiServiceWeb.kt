package com.gold.chefood.WebApi

import com.gold.chefood.models.NewsResponse
import com.gold.chefood.models.SearchRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService{
    @Headers("Content-Type: application/json")
    @POST("images")
    fun searchNews(
        @retrofit2.http.Header("X-API-KEY") apiKey:String,
        @Body request: SearchRequest
    ): Call<NewsResponse>
}