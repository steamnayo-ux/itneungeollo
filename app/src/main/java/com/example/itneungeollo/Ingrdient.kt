package com.example.itneungeollo

data class Ingredient(
    val name: String,
    val category: IngredientCategory,
    val aliases: List<String> = emptyList()
)

enum class IngredientCategory {
    VEGETABLE,
    MEAT,
    SEAFOOD,
    DAIRY_EGG,
    FRUIT,
    GRAIN_NOODLE,
    PROCESSED,
    SEASONING
}

val ingredients = listOf(

    // 🥬 채소
    Ingredient(
        name = "양파",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "대파",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "감자",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "애호박",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "콩나물",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "양배추",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "버섯",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "오이",
        category = IngredientCategory.VEGETABLE
    ),
    Ingredient(
        name = "토마토",
        category = IngredientCategory.VEGETABLE
    ),

    // 🥩 육류
    Ingredient(
        name = "돼지고기",
        category = IngredientCategory.MEAT
    ),
    Ingredient(
        name = "닭고기",
        category = IngredientCategory.MEAT
    ),

    // 🐟 수산물
    Ingredient(
        name = "참치",
        category = IngredientCategory.SEAFOOD
    ),

    // 🥚 계란·유제품
    Ingredient(
        name = "계란",
        category = IngredientCategory.DAIRY_EGG,
        aliases = listOf("달걀")
    ),
    Ingredient(
        name = "치즈",
        category = IngredientCategory.DAIRY_EGG
    ),

    // 🌾 곡류·면
    Ingredient(
        name = "밥",
        category = IngredientCategory.GRAIN_NOODLE
    ),
    Ingredient(
        name = "소면",
        category = IngredientCategory.GRAIN_NOODLE
    ),
    Ingredient(
        name = "막국수면",
        category = IngredientCategory.GRAIN_NOODLE
    ),

    // 🥫 가공식품
    Ingredient(
        name = "김치",
        category = IngredientCategory.PROCESSED
    ),
    Ingredient(
        name = "두부",
        category = IngredientCategory.PROCESSED
    ),
    Ingredient(
        name = "햄",
        category = IngredientCategory.PROCESSED
    ),
    Ingredient(
        name = "소시지",
        category = IngredientCategory.PROCESSED,
        aliases = listOf("소세지")
    ),
    Ingredient(
        name = "부침가루",
        category = IngredientCategory.PROCESSED
    ),
    Ingredient(
        name = "어묵",
        category = IngredientCategory.PROCESSED
    ),
    Ingredient(
        name = "떡",
        category = IngredientCategory.PROCESSED
    ),
    Ingredient(
        name = "옥수수",
        category = IngredientCategory.PROCESSED
    ),
    Ingredient(
        name = "마요네즈",
        category = IngredientCategory.PROCESSED,
        aliases = listOf("마요")
    ),

    // 🧂 양념·조미료
    Ingredient(
        name = "소금",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "설탕",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "진간장",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "식용유",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "참기름",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "들기름",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "고춧가루",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "고추장",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "된장",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "다진 마늘",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "후추",
        category = IngredientCategory.SEASONING
    ),
    Ingredient(
        name = "식초",
        category = IngredientCategory.SEASONING
    )
)

