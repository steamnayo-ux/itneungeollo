package com.example.itneungeollo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    var screen by mutableStateOf("home")
        private set

    var selectedIngredients by mutableStateOf(setOf<String>())
        private set

    var selectedRecipe by mutableStateOf<Recipe?>(null)
        private set

    fun goToIngredients() {
        screen = "ingredients"
    }

    fun goToRecommend() {
        screen = "recommend"
    }

    fun updateIngredients(ingredients: Set<String>) {
        selectedIngredients = ingredients
    }

    fun selectRecipe(recipe: Recipe) {
        selectedRecipe = recipe
        screen = "detail"
    }

    // 뒤로가기 처리도 여기서 관리하면 편해요
    fun goBack(): Boolean {
        return when (screen) {
            "detail" -> {
                screen = "recommend"
                true
            }
            "recommend" -> {
                screen = "ingredients"
                true
            }
            "ingredients" -> {
                screen = "home"
                true
            }
            else -> false // home에서는 뒤로가기 시 앱 종료 허용
        }
    }
}