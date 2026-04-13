package com.gold.chefood

data class RecipeStep(
    val step: String,
    val value: String
)

data class RecipeApiItem(
    val id: Int,
    val name: String,
    val favorite: Boolean,
    val type: List<String>,
    val imageUrl: String,
    val description: String,
    val calories: String,
    val carbohydrates: String,
    val protein: String,
    val totalFat: String,
    val ingredients: List<String>,
    val steps: List<RecipeStep>
)

data class VideoFood(
    val id: Int,
    val title: String,
    val description: String,
    val calories: Int,
    val thumbnailUrl: String,
    val videoUrl: String,
    val isFavorite: Boolean
)

data class WebFoodResource(
    val id: Int,
    val title: String,
    val description: String,
    val keywords: List<String>,
    val url: String
)
