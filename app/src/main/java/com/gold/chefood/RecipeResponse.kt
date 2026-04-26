package com.gold.chefood
import android.os.Parcelable
import java.io.Serializable

data class RecipeResponse (
    val recipes: List<Recipe>
): Serializable

data class Recipe(
    val id: Int,
    val name: String,
    var favorite: Boolean,
    val type: List<String>,
    val image_url: String,
    val video:  String,
    val description: String,
    val calories: String?,
    val carbohydrates: String,
    val protein: String,
    val totalfat: String,
    val ingredients: List<String>,
    val steps: List<Step>
): Serializable

data class Step(
    val step: String,
    val value: String
): Serializable