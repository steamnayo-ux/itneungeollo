package com.example.itneungeollo

import androidx.room.Entity
import androidx.room.PrimaryKey

// recipes.json의 레시피 자체는 그대로 두고,
// 사용자가 남긴 부가 정보(즐겨찾기/최근 본/평점/메모)만 recipeId로 연결해서 저장해요.
@Entity(tableName = "user_recipe_data")
data class UserRecipeData(
    @PrimaryKey
    val recipeId: Int,
    val isFavorite: Boolean = false,
    val lastViewedAt: Long? = null,
    val rating: Int? = null, // 1~5, 아직 평가 안 했으면 null
    val memo: String? = null
)