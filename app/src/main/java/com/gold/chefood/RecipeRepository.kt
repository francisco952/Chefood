package com.gold.chefood

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class RecipeRepository(private val context: Context) {

    private val stateStore = RecipeStateStore(context)

    suspend fun getRecipes(): List<RecipeApiItem> = withContext(Dispatchers.IO) {
        val remoteRaw = fetchRemoteJson()
        val raw = if (remoteRaw.isNullOrBlank()) {
            context.assets.open(ASSET_FILE).bufferedReader().use { it.readText() }
        } else {
            remoteRaw
        }

        val root = JSONObject(raw)
        val recipesArray = root.optJSONArray(KEY_RECIPES) ?: JSONArray()
        val deletedIds = stateStore.getDeletedIds()
        val favoriteIds = stateStore.getFavoriteIds()

        buildList {
            for (index in 0 until recipesArray.length()) {
                val item = recipesArray.optJSONObject(index) ?: continue
                val recipe = item.toRecipeApiItem()
                if (!deletedIds.contains(recipe.id)) {
                    add(recipe.copy(favorite = favoriteIds.contains(recipe.id) || recipe.favorite))
                }
            }
        }
    }

    suspend fun getVideoFoods(): List<VideoFood> {
        val videos = sampleVideos()
        return getRecipes().mapIndexed { index, recipe ->
            VideoFood(
                id = recipe.id,
                title = recipe.name,
                description = recipe.description,
                calories = recipe.calories.onlyDigits().toIntOrNull() ?: 0,
                thumbnailUrl = recipe.imageUrl,
                videoUrl = videos[index % videos.size],
                isFavorite = recipe.favorite
            )
        }
    }

    // Punto de reutilizacion para otros modulos del equipo:
    // crear aqui metodos de mapeo (por ejemplo, getListFoods o getProfileStats)
    // evita duplicar parsing del JSON en cada fragment.
    suspend fun getWebResources(): List<WebFoodResource> {
        return getRecipes().map { recipe ->
            val query = buildSearchQuery(recipe)
            WebFoodResource(
                id = recipe.id,
                title = recipe.name,
                description = recipe.description,
                keywords = (recipe.type + recipe.ingredients).distinct(),
                url = "https://www.google.com/search?q=${Uri.encode(query)}"
            )
        }
    }

    suspend fun setFavorite(recipeId: Int, isFavorite: Boolean) {
        stateStore.setFavorite(recipeId, isFavorite)
    }

    suspend fun setDeleted(recipeId: Int, isDeleted: Boolean) {
        stateStore.setDeleted(recipeId, isDeleted)
    }

    private fun fetchRemoteJson(): String? {
        val endpoint = BuildConfig.RECIPES_API_URL.trim()
        if (endpoint.isBlank()) return null

        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 5000
            readTimeout = 5000
        }

        return try {
            if (connection.responseCode in 200..299) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                null
            }
        } catch (_: Exception) {
            null
        } finally {
            connection.disconnect()
        }
    }

    private fun JSONObject.toRecipeApiItem(): RecipeApiItem {
        return RecipeApiItem(
            id = optInt("id"),
            name = optString("name"),
            favorite = optBoolean("favorite"),
            type = optStringList("type"),
            imageUrl = optString("image_url"),
            description = optString("description"),
            calories = optString("calories"),
            carbohydrates = optString("carbohydrates"),
            protein = optString("protein"),
            totalFat = optString("totalfat"),
            ingredients = optStringList("ingredients"),
            steps = optStepList("steps")
        )
    }

    private fun JSONObject.optStringList(key: String): List<String> {
        val array = optJSONArray(key) ?: return emptyList()
        return buildList {
            for (i in 0 until array.length()) {
                add(array.optString(i))
            }
        }
    }

    private fun JSONObject.optStepList(key: String): List<RecipeStep> {
        val array = optJSONArray(key) ?: return emptyList()
        return buildList {
            for (i in 0 until array.length()) {
                val stepObj = array.optJSONObject(i) ?: continue
                add(
                    RecipeStep(
                        step = stepObj.optString("step"),
                        value = stepObj.optString("value")
                    )
                )
            }
        }
    }

    private fun buildSearchQuery(recipe: RecipeApiItem): String {
        val topIngredients = recipe.ingredients.take(3).joinToString(" ")
        return "receta ${recipe.name} ${topIngredients}".trim()
    }

    private fun sampleVideos(): List<String> {
        return listOf(
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
        )
    }

    private fun String.onlyDigits(): String = filter { it.isDigit() }

    companion object {
        private const val ASSET_FILE = "recipes.json"
        private const val KEY_RECIPES = "recipes"
    }
}
