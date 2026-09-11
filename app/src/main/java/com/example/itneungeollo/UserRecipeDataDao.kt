package com.example.itneungeollo

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRecipeDataDao {

    // 전체 데이터를 실시간으로 관찰 (즐겨찾기 목록, 최근 본 목록을 화면에서 바로 반영하기 위해)
    @Query("SELECT * FROM user_recipe_data")
    fun observeAll(): Flow<List<UserRecipeData>>

    @Query("SELECT * FROM user_recipe_data WHERE recipeId = :recipeId")
    suspend fun getByRecipeId(recipeId: Int): UserRecipeData?

    // 이미 있으면 업데이트, 없으면 새로 삽입 (Room 2.5+ @Upsert)
    @Upsert
    suspend fun upsert(data: UserRecipeData)

    @Query("SELECT * FROM user_recipe_data WHERE isFavorite = 1")
    fun observeFavorites(): Flow<List<UserRecipeData>>

    @Query(
        "SELECT * FROM user_recipe_data " +
                "WHERE lastViewedAt IS NOT NULL " +
                "ORDER BY lastViewedAt DESC " +
                "LIMIT :limit"
    )
    fun observeRecentlyViewed(limit: Int = 20): Flow<List<UserRecipeData>>
}