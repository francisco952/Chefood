package com.gold.chefood

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.recipeDataStore by preferencesDataStore(name = "recipe_state")

class RecipeStateStore(private val context: Context) {

    suspend fun setFavorite(recipeId: Int, isFavorite: Boolean) {
        context.recipeDataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            val normalizedId = recipeId.toString()
            if (isFavorite) {
                current.add(normalizedId)
            } else {
                current.remove(normalizedId)
            }
            prefs[FAVORITES_KEY] = current
        }
    }

    suspend fun setDeleted(recipeId: Int, isDeleted: Boolean) {
        context.recipeDataStore.edit { prefs ->
            val current = prefs[DELETED_KEY]?.toMutableSet() ?: mutableSetOf()
            val normalizedId = recipeId.toString()
            if (isDeleted) {
                current.add(normalizedId)
            } else {
                current.remove(normalizedId)
            }
            prefs[DELETED_KEY] = current
        }
    }

    suspend fun getFavoriteIds(): Set<Int> {
        val stored = context.recipeDataStore.data.first()[FAVORITES_KEY].orEmpty()
        return stored.mapNotNull { it.toIntOrNull() }.toSet()
    }

    suspend fun getDeletedIds(): Set<Int> {
        val stored = context.recipeDataStore.data.first()[DELETED_KEY].orEmpty()
        return stored.mapNotNull { it.toIntOrNull() }.toSet()
    }

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_recipe_ids")
        private val DELETED_KEY = stringSetPreferencesKey("deleted_recipe_ids")
    }
}
