package com.gold.chefood

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun loadJSONFromAsset(context:  Context): String {
    return context.assets.open("recipes.json")
        .bufferedReader()
        .use { it.readText() }
}

fun getRecipes(context: Context): List<Recipe> {
    val json = loadJSONFromAsset(context)

    val gson = Gson()
    val type = object : TypeToken<RecipeResponse>() {}.type

    val response: RecipeResponse = gson.fromJson(json, type)

    return response.recipes
}