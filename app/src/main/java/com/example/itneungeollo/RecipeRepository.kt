
package com.example.itneungeollo

import android.content.Context
import org.json.JSONArray

object RecipeRepository {

    fun loadRecipes(context: Context): List<Recipe> {

        val json = context.assets
            .open("recipes.json")
            .bufferedReader()
            .use { it.readText() }

        val jsonArray = JSONArray(json)

        val recipes = mutableListOf<Recipe>()

        for (i in 0 until jsonArray.length()) {

            val item = jsonArray.getJSONObject(i)

            val ingredients = mutableListOf<String>()
            val optionalIngredients = mutableListOf<String>()
            val seasonings = mutableListOf<String>()
            val steps = mutableListOf<String>()

            val ingredientsArray = item.optJSONArray("ingredients")
            if (ingredientsArray != null) {
                for (j in 0 until ingredientsArray.length()) {
                    ingredients.add(ingredientsArray.getString(j))
                }
            }

            val optionalArray = item.optJSONArray("optionalIngredients")
            if (optionalArray != null) {
                for (j in 0 until optionalArray.length()) {
                    optionalIngredients.add(optionalArray.getString(j))
                }
            }

            val seasoningsArray = item.optJSONArray("seasonings")
            if (seasoningsArray != null) {
                for (j in 0 until seasoningsArray.length()) {
                    seasonings.add(seasoningsArray.getString(j))
                }
            }

            val stepsArray = item.optJSONArray("steps")
            if (stepsArray != null) {
                for (j in 0 until stepsArray.length()) {
                    steps.add(stepsArray.getString(j))
                }
            }

            recipes.add(
                Recipe(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    time = item.getInt("time"),
                    ingredients = ingredients,
                    optionalIngredients = optionalIngredients,
                    seasonings = seasonings,
                    steps = steps
                )
            )
        }

        return recipes
    }
}