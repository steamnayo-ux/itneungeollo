package com.example.itneungeollo

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    var screen by mutableStateOf("home")
        private set

    var selectedIngredients by mutableStateOf(setOf<String>())
        private set

    var selectedRecipe by mutableStateOf<Recipe?>(null)
        private set

    // 상세 화면에서 뒤로가기 눌렀을 때 어디로 돌아갈지 (추천 목록 vs 즐겨찾기 목록)
    private var detailOrigin by mutableStateOf("recommend")

    // =====================================================
    // 즐겨찾기 / 최근 본 / 평점 (Room DB)
    // =====================================================

    private val userRecipeDataDao =
        AppDatabase.getInstance(application).userRecipeDataDao()

    // recipeId -> UserRecipeData 로 바로 찾을 수 있게 맵 형태로 들고 있어요
    var userRecipeDataMap by mutableStateOf<Map<Int, UserRecipeData>>(emptyMap())
        private set

    init {
        viewModelScope.launch {
            userRecipeDataDao.observeAll().collect { list ->
                userRecipeDataMap = list.associateBy { it.recipeId }
            }
        }
    }

    fun isFavorite(recipeId: Int): Boolean =
        userRecipeDataMap[recipeId]?.isFavorite == true

    fun getRating(recipeId: Int): Int? =
        userRecipeDataMap[recipeId]?.rating

    fun getMemo(recipeId: Int): String? =
        userRecipeDataMap[recipeId]?.memo

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            val current = userRecipeDataMap[recipeId]
            userRecipeDataDao.upsert(
                (current ?: UserRecipeData(recipeId = recipeId))
                    .copy(isFavorite = current?.isFavorite != true)
            )
        }
    }

    fun setRating(recipeId: Int, rating: Int) {
        viewModelScope.launch {
            val current = userRecipeDataMap[recipeId]
            userRecipeDataDao.upsert(
                (current ?: UserRecipeData(recipeId = recipeId))
                    .copy(rating = rating)
            )
        }
    }

    fun setMemo(recipeId: Int, memo: String) {
        viewModelScope.launch {
            val current = userRecipeDataMap[recipeId]
            userRecipeDataDao.upsert(
                (current ?: UserRecipeData(recipeId = recipeId))
                    .copy(memo = memo)
            )
        }
    }

    private fun markViewed(recipeId: Int) {
        viewModelScope.launch {
            val current = userRecipeDataMap[recipeId]
            userRecipeDataDao.upsert(
                (current ?: UserRecipeData(recipeId = recipeId))
                    .copy(lastViewedAt = System.currentTimeMillis())
            )
        }
    }

    // =====================================================
    // 다크모드 설정 (SharedPreferences)
    // =====================================================

    private val themePreferences = ThemePreferences(application)

    var isDarkMode by mutableStateOf(themePreferences.isDarkMode())
        private set

    fun toggleDarkMode() {
        isDarkMode = !isDarkMode
        themePreferences.setDarkMode(isDarkMode)
    }

    fun goToIngredients() {
        screen = "ingredients"
    }

    fun goToFavorites() {
        screen = "favorites"
    }

    fun goToRecent() {
        screen = "recent"
    }

    fun goToRecommend() {
        screen = "recommend"
    }

    fun updateIngredients(ingredients: Set<String>) {
        selectedIngredients = ingredients
    }

    fun selectRecipe(recipe: Recipe) {
        selectedRecipe = recipe
        detailOrigin = screen // "recommend" 또는 "favorites"에서 들어온 걸 기억
        screen = "detail"
        markViewed(recipe.id)
    }

    // 뒤로가기 처리도 여기서 관리하면 편해요
    fun goBack(): Boolean {
        return when (screen) {
            "detail" -> {
                screen = detailOrigin
                true
            }
            "recommend" -> {
                screen = "ingredients"
                true
            }
            "favorites" -> {
                screen = "home"
                true
            }
            "recent" -> {
                screen = "home"
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