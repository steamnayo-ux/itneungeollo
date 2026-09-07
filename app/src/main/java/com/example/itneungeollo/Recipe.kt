package com.example.itneungeollo


// =====================================================
// 기본 양념
// =====================================================

val basicSeasonings = setOf(
    "소금",
    "설탕",
    "진간장",
    "식용유",
    "참기름",
    "고춧가루",
    "고추장",
    "된장",
    "다진 마늘",
    "후추",
    "식초"
)


// =====================================================
// 레시피 데이터
// =====================================================

data class Recipe(
    val id: Int,
    val name: String,
    val time: Int,

    // 반드시 필요한 일반 재료
    val ingredients: List<String>,

    // 없어도 조리 가능한 재료
    val optionalIngredients: List<String>,

    // 기본 양념 중 이 레시피에서 실제 사용하는 양념
    val seasonings: List<String>,

    // 만드는 방법
    val steps: List<String>,

    val emoji: String = "🍽️"
)

data class RecipeRecommendation(
    val recipe: Recipe,
    val matchedRequired: Int,
    val missingRequired: List<String>,
    val matchedOptional: Int,
    val score: Double
)


// =====================================================
// 레시피 추천
// =====================================================

fun recommendRecipes(
    userIngredients: Set<String>,
    recipes: List<Recipe>
): List<RecipeRecommendation> {

    val normalizedUserIngredients =
        userIngredients
            .map { normalizeIngredient(it) }
            .toSet()

    return recipes.mapNotNull { recipe ->

        val requiredIngredients =
            recipe.ingredients
                .map { normalizeIngredient(it) }
                .distinct()

        if (requiredIngredients.isEmpty()) {
            return@mapNotNull null
        }

        // 필수 재료 중 가지고 있는 재료
        val matchedRequiredIngredients =
            requiredIngredients.filter {
                it in normalizedUserIngredients
            }

        val matchedRequired =
            matchedRequiredIngredients.size

        // 없는 필수 재료
        val missingRequired =
            requiredIngredients.filter {
                it !in normalizedUserIngredients
            }

        // 필수 재료 충족률
        val requiredRatio =
            matchedRequired.toDouble() /
                    requiredIngredients.size

        // 있으면 좋은 재료 중 가지고 있는 재료
        val matchedOptional =
            recipe.optionalIngredients
                .map { normalizeIngredient(it) }
                .distinct()
                .count {
                    it in normalizedUserIngredients
                }

        // 점수 계산
        var score =
            requiredRatio * 100

        // 선택 재료 하나당 +5점
        score += matchedOptional * 5

        // 조리 시간이 짧을수록 보너스
        score += when {
            recipe.time <= 10 -> 20
            recipe.time <= 20 -> 15
            recipe.time <= 30 -> 10
            recipe.time <= 40 -> 5
            else -> 0
        }

        // 필수 재료가 하나도 없으면 제외
        if (matchedRequired == 0) {
            return@mapNotNull null
        }

        RecipeRecommendation(
            recipe = recipe,
            matchedRequired = matchedRequired,
            missingRequired = missingRequired,
            matchedOptional = matchedOptional,
            score = score
        )
    }
        .sortedByDescending { it.score }
        .take(20)
}

// =====================================================
// JSON에서 레시피 불러오기
// =====================================================

fun loadRecipesFromAssets(context: android.content.Context): List<Recipe> {

    val jsonString =
        context.assets
            .open("recipes.json")
            .bufferedReader(Charsets.UTF_8)
            .use { it.readText() }

    val jsonArray = org.json.JSONArray(jsonString)

    val result = mutableListOf<Recipe>()

    for (i in 0 until jsonArray.length()) {

        val obj = jsonArray.getJSONObject(i)

        fun jsonArrayToList(key: String): List<String> {
            val arr = obj.getJSONArray(key)
            val list = mutableListOf<String>()
            for (j in 0 until arr.length()) {
                list.add(arr.getString(j))
            }
            return list
        }

        result.add(
            Recipe(
                id = obj.getInt("id"),
                name = obj.getString("name"),
                time = obj.getInt("time"),
                ingredients = jsonArrayToList("ingredients"),
                optionalIngredients = jsonArrayToList("optionalIngredients"),
                seasonings = jsonArrayToList("seasonings"),
                steps = jsonArrayToList("steps") ,
                emoji = obj.optString("emoji", "🍽️")
            )
        )
    }

    return result
}