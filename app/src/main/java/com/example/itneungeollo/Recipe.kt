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
    val steps: List<String>
)

data class RecipeRecommendation(
    val recipe: Recipe,
    val matchedRequired: Int,
    val missingRequired: List<String>,
    val matchedOptional: Int,
    val score: Double
)


// =====================================================
// 레시피 목록
// =====================================================

val recipes = listOf(

    // -------------------------------------------------
    // 1. 김치볶음밥
    // -------------------------------------------------

    Recipe(
        id = 1,
        name = "김치볶음밥",
        time = 15,

        ingredients = listOf(
            "밥",
            "김치"
        ),

        optionalIngredients = listOf(
            "계란",
            "대파",
            "김가루"
        ),

        seasonings = listOf(
            "식용유",
            "참기름",
            "후추"
        ),

        steps = listOf(
            "김치를 잘게 썬다.",
            "대파가 있다면 잘게 썬다.",
            "팬에 식용유를 두르고 김치를 볶는다.",
            "대파가 있다면 넣고 함께 볶는다.",
            "밥을 넣고 함께 볶는다.",
            "후추를 넣어 간을 맞춘다.",
            "기호에 따라 계란과 김가루를 올린다.",
            "불을 끄고 참기름을 넣어 마무리한다."
        )
    ),


    // -------------------------------------------------
    // 2. 계란밥
    // -------------------------------------------------

    Recipe(
        id = 2,
        name = "계란밥",
        time = 10,

        ingredients = listOf(
            "밥",
            "계란"
        ),

        optionalIngredients = listOf(
            "김가루"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "참기름"
        ),

        steps = listOf(
            "팬에 식용유를 두른다.",
            "계란을 프라이한다.",
            "밥 위에 계란을 올린다.",
            "진간장과 참기름을 넣는다.",
            "기호에 따라 김가루를 뿌린다.",
            "잘 비벼서 먹는다."
        )
    ),


    // -------------------------------------------------
    // 3. 김치 계란덮밥
    // -------------------------------------------------

    Recipe(
        id = 3,
        name = "김치 계란덮밥",
        time = 15,

        ingredients = listOf(
            "밥",
            "김치",
            "계란"
        ),

        optionalIngredients = listOf(
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "참기름"
        ),

        steps = listOf(
            "김치를 잘게 썬다.",
            "대파가 있다면 잘게 썬다.",
            "팬에 식용유를 두르고 김치를 볶는다.",
            "대파가 있다면 넣고 함께 볶는다.",
            "계란을 넣고 함께 볶는다.",
            "진간장을 넣어 간을 맞춘다.",
            "밥을 그릇에 담고 볶은 재료를 올린다.",
            "참기름을 조금 넣어 마무리한다."
        )
    ),


    // -------------------------------------------------
    // 4. 계란국
    // -------------------------------------------------

    Recipe(
        id = 4,
        name = "계란국",
        time = 15,

        ingredients = listOf(
            "계란"
        ),

        optionalIngredients = listOf(
            "대파"
        ),

        seasonings = listOf(
            "소금",
            "후추"
        ),

        steps = listOf(
            "냄비에 물을 끓인다.",
            "계란을 풀어준다.",
            "끓는 물에 계란을 천천히 넣는다.",
            "대파를 넣는다.",
            "소금과 후추로 간을 한다.",
            "조금 더 끓여 완성한다."
        )
    ),


    // -------------------------------------------------
    // 5. 참치마요 덮밥
    // -------------------------------------------------

    Recipe(
        id = 5,
        name = "참치마요 덮밥",
        time = 10,

        ingredients = listOf(
            "밥",
            "참치",
            "마요네즈"
        ),
        optionalIngredients = listOf(
            "계란",
            "김가루"
        ),

        seasonings = listOf(
            "식용유",
            "진간장"
        ),

        steps = listOf(
            "참치의 기름을 뺀다.",
            "팬에 식용유를 두르고 참치를 살짝 볶는다.",
            "진간장을 조금 넣어 간을 한다.",
            "밥을 그릇에 담는다.",
            "참치를 밥 위에 올린다.",
            "마요네즈를 뿌린다.",
            "기호에 따라 계란과 김가루를 올린다."
        )
    ),


    // -------------------------------------------------
    // 6. 참치볶음밥
    // -------------------------------------------------

    Recipe(
        id = 6,
        name = "참치볶음밥",
        time = 15,

        ingredients = listOf(
            "밥",
            "참치"
        ),

        optionalIngredients = listOf(
            "계란",
            "양파",
            "대파",
            "김치"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "후추",
            "참기름"
        ),

        steps = listOf(
            "참치의 기름을 뺀다.",
            "팬에 식용유를 두른다.",
            "양파와 대파가 있다면 먼저 볶는다.",
            "참치를 넣고 볶는다.",
            "밥을 넣고 함께 볶는다.",
            "진간장과 후추로 간을 한다.",
            "불을 끄고 참기름을 넣는다."
        )
    ),


    // -------------------------------------------------
    // 7. 햄 볶음밥
    // -------------------------------------------------

    Recipe(
        id = 7,
        name = "햄 볶음밥",
        time = 15,

        ingredients = listOf(
            "밥",
            "햄"
        ),

        optionalIngredients = listOf(
            "계란",
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "소금",
            "후추"
        ),

        steps = listOf(
            "햄을 먹기 좋은 크기로 자른다.",
            "식용유를 두르고 햄을 볶는다.",
            "양파와 대파가 있다면 넣고 함께 볶는다.",
            "밥을 넣고 함께 볶는다.",
            "소금과 후추로 간을 한다.",
            "기호에 따라 계란을 추가한다."
        )
    ),


    // -------------------------------------------------
    // 8. 계란 햄 볶음밥
    // -------------------------------------------------

    Recipe(
        id = 8,
        name = "계란 햄 볶음밥",
        time = 15,

        ingredients = listOf(
            "밥",
            "햄",
            "계란"
        ),

        optionalIngredients = listOf(
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "소금",
            "후추"
        ),

        steps = listOf(
            "식용유를 두른 팬에 햄을 볶는다.",
            "양파와 대파를 넣는다.",
            "밥을 넣고 볶는다.",
            "계란을 넣고 함께 볶는다.",
            "소금과 후추로 간을 한다."
        )
    ),


    // -------------------------------------------------
    // 9. 두부구이
    // -------------------------------------------------

    Recipe(
        id = 9,
        name = "두부구이",
        time = 15,

        ingredients = listOf(
            "두부"
        ),

        optionalIngredients = listOf(
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "참기름"
        ),

        steps = listOf(
            "두부의 물기를 뺀다.",
            "두부를 먹기 좋은 크기로 자른다.",
            "팬에 식용유를 두르고 두부를 굽는다.",
            "진간장을 곁들인다.",
            "대파와 참기름을 올린다."
        )
    ),


    // -------------------------------------------------
    // 10. 두부 계란부침
    // -------------------------------------------------

    Recipe(
        id = 10,
        name = "두부 계란부침",
        time = 15,

        ingredients = listOf(
            "두부",
            "계란"
        ),

        optionalIngredients = listOf(
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "소금",
            "후추"
        ),

        steps = listOf(
            "두부를 먹기 좋은 크기로 자른다.",
            "계란을 풀고 소금으로 간한다.",
            "두부에 계란물을 입힌다.",
            "식용유를 두른 팬에 노릇하게 굽는다.",
            "후추를 조금 뿌린다.",
            "기호에 따라 대파를 곁들인다."
        )
    ),


    // -------------------------------------------------
    // 11. 두부김치
    // -------------------------------------------------

    Recipe(
        id = 11,
        name = "두부김치",
        time = 20,

        ingredients = listOf(
            "두부",
            "김치"
        ),

        optionalIngredients = listOf(
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "참기름",
            "설탕"
        ),

        steps = listOf(
            "두부를 따뜻하게 데운다.",
            "김치를 먹기 좋은 크기로 자른다.",
            "식용유를 두르고 김치를 볶는다.",
            "설탕을 조금 넣어 간을 조절한다.",
            "두부와 볶은 김치를 함께 담는다.",
            "대파가 있다면 올린다.",
            "참기름을 조금 뿌린다."
        )
    ),


    // -------------------------------------------------
    // 12. 김치찌개
    // -------------------------------------------------

    Recipe(
        id = 12,
        name = "김치찌개",
        time = 25,

        ingredients = listOf(
            "김치"
        ),

        optionalIngredients = listOf(
            "두부",
            "대파",
            "양파"
        ),

        seasonings = listOf(
            "고춧가루",
            "다진 마늘",
            "진간장",
            "설탕"
        ),

        steps = listOf(
            "김치를 먹기 좋은 크기로 자른다.",
            "냄비에 김치를 넣고 볶는다.",
            "고춧가루와 다진 마늘을 넣는다.",
            "물을 넣고 끓인다.",
            "진간장과 설탕으로 간을 맞춘다.",
            "두부와 채소가 있다면 넣고 끓인다."
        )
    ),


    // -------------------------------------------------
    // 13. 두부김치찌개
    // -------------------------------------------------

    Recipe(
        id = 13,
        name = "두부김치찌개",
        time = 25,

        ingredients = listOf(
            "김치",
            "두부"
        ),

        optionalIngredients = listOf(
            "대파",
            "양파"
        ),

        seasonings = listOf(
            "고춧가루",
            "다진 마늘",
            "진간장"
        ),

        steps = listOf(
            "김치를 잘게 썬다.",
            "냄비에 김치를 볶는다.",
            "고춧가루와 다진 마늘을 넣는다.",
            "물을 넣고 끓인다.",
            "두부를 넣는다.",
            "진간장으로 간을 맞춘다.",
            "대파와 양파가 있다면 넣고 끓인다."
        )
    ),


    // -------------------------------------------------
    // 14. 감자볶음
    // -------------------------------------------------

    Recipe(
        id = 14,
        name = "감자볶음",
        time = 15,

        ingredients = listOf(
            "감자"
        ),

        optionalIngredients = listOf(
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "소금",
            "후추"
        ),

        steps = listOf(
            "감자를 얇게 썬다.",
            "식용유를 두른 팬에 감자를 볶는다.",
            "양파가 있다면 넣는다.",
            "소금과 후추로 간한다.",
            "감자가 익으면 대파가 있다면 넣고 마무리한다."
        )
    ),


    // -------------------------------------------------
    // 15. 감자 계란볶음
    // -------------------------------------------------

    Recipe(
        id = 15,
        name = "감자 계란볶음",
        time = 20,

        ingredients = listOf(
            "감자",
            "계란"
        ),

        optionalIngredients = listOf(
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "소금",
            "후추"
        ),

        steps = listOf(
            "감자를 얇게 썬다.",
            "식용유를 두른 팬에 감자를 볶는다.",
            "양파가 있다면 넣고 익힌다.",
            "계란을 넣고 함께 볶는다.",
            "소금과 후추로 간한다.",
            "대파가 있다면 넣고 마무리한다."
        )
    ),


    // -------------------------------------------------
    // 16. 감자조림
    // -------------------------------------------------

    Recipe(
        id = 16,
        name = "감자조림",
        time = 25,

        ingredients = listOf(
            "감자"
        ),

        optionalIngredients = listOf(
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "진간장",
            "설탕",
            "식용유"
        ),

        steps = listOf(
            "감자를 큼직하게 자른다.",
            "냄비에 식용유를 조금 두른다.",
            "감자를 넣고 살짝 볶는다.",
            "물을 넣고 진간장과 설탕을 넣는다.",
            "중불에서 감자가 익을 때까지 졸인다.",
            "대파가 있다면 넣고 마무리한다."
        )
    ),


    // -------------------------------------------------
    // 17. 햄 계란부침
    // -------------------------------------------------

    Recipe(
        id = 17,
        name = "햄 계란부침",
        time = 10,

        ingredients = listOf(
            "햄",
            "계란"
        ),

        optionalIngredients = listOf(
            "대파",
            "양파"
        ),

        seasonings = listOf(
            "식용유",
            "소금",
            "후추"
        ),

        steps = listOf(
            "햄을 얇게 썬다.",
            "계란을 풀고 소금과 후추로 간한다.",
            "햄에 계란물을 입힌다.",
            "식용유를 두른 팬에서 노릇하게 굽는다.",
            "대파나 양파가 있다면 곁들인다."
        )
    ),


    // -------------------------------------------------
    // 18. 햄 김치볶음
    // -------------------------------------------------

    Recipe(
        id = 18,
        name = "햄 김치볶음",
        time = 15,

        ingredients = listOf(
            "햄",
            "김치"
        ),

        optionalIngredients = listOf(
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "설탕",
            "고춧가루"
        ),

        steps = listOf(
            "햄을 먹기 좋은 크기로 자른다.",
            "김치를 잘게 썬다.",
            "식용유를 두른 팬에 햄을 볶는다.",
            "김치를 넣고 볶는다.",
            "설탕과 고춧가루를 넣어 간을 맞춘다.",
            "대파와 양파가 있다면 넣고 볶는다."
        )
    ),


    // -------------------------------------------------
    // 19. 김치전
    // -------------------------------------------------

    Recipe(
        id = 19,
        name = "김치전",
        time = 20,

        ingredients = listOf(
            "김치",
            "부침가루"
        ),

        optionalIngredients = listOf(
            "양파",
            "대파",
            "계란"
        ),

        seasonings = listOf(
            "식용유",
            "소금"
        ),

        steps = listOf(
            "김치를 잘게 썬다.",
            "부침가루와 물을 섞어 반죽을 만든다.",
            "김치와 반죽을 섞는다.",
            "팬에 식용유를 두른다.",
            "반죽을 얇게 펼친다.",
            "앞뒤로 노릇하게 굽는다."
        )
    ),


    // -------------------------------------------------
    // 20. 감자전
    // -------------------------------------------------

    Recipe(
        id = 20,
        name = "감자전",
        time = 25,

        ingredients = listOf(
            "감자"
        ),

        optionalIngredients = listOf(
            "양파"
        ),

        seasonings = listOf(
            "식용유",
            "소금"
        ),

        steps = listOf(
            "감자를 깨끗이 씻고 껍질을 벗긴다.",
            "감자를 곱게 간다.",
            "소금으로 간한다.",
            "팬에 식용유를 두른다.",
            "감자 반죽을 얇게 펼친다.",
            "앞뒤로 노릇하게 굽는다."
        )
    ),


    // -------------------------------------------------
    // 21. 계란찜
    // -------------------------------------------------

    Recipe(
        id = 21,
        name = "계란찜",
        time = 15,

        ingredients = listOf(
            "계란"
        ),

        optionalIngredients = listOf(
            "대파"
        ),

        seasonings = listOf(
            "소금",
            "후추"
        ),

        steps = listOf(
            "계란을 풀어준다.",
            "물을 넣고 잘 섞는다.",
            "소금으로 간한다.",
            "대파를 넣는다.",
            "냄비나 전자레인지에서 익힌다.",
            "후추를 조금 뿌려 마무리한다."
        )
    ),


    // -------------------------------------------------
    // 22. 계란말이
    // -------------------------------------------------

    Recipe(
        id = 22,
        name = "계란말이",
        time = 15,

        ingredients = listOf(
            "계란"
        ),

        optionalIngredients = listOf(
            "대파",
            "햄",
            "양파"
        ),

        seasonings = listOf(
            "식용유",
            "소금"
        ),

        steps = listOf(
            "계란을 풀어준다.",
            "소금으로 간한다.",
            "대파와 양파가 있다면 잘게 썬다.",
            "계란과 채소를 섞는다.",
            "팬에 식용유를 두르고 계란물을 얇게 부친다.",
            "돌돌 말아 익힌다."
        )
    ),


    // -------------------------------------------------
    // 23. 참치 계란볶음
    // -------------------------------------------------

    Recipe(
        id = 23,
        name = "참치 계란볶음",
        time = 10,

        ingredients = listOf(
            "참치",
            "계란"
        ),

        optionalIngredients = listOf(
            "대파",
            "양파"
        ),

        seasonings = listOf(
            "식용유",
            "소금",
            "후추"
        ),

        steps = listOf(
            "참치의 기름을 뺀다.",
            "식용유를 두른 팬에 대파와 양파가 있다면 볶는다.",
            "참치를 넣는다.",
            "계란을 넣고 볶는다.",
            "소금과 후추로 간한다."
        )
    ),


    // -------------------------------------------------
    // 24. 참치김치볶음
    // -------------------------------------------------

    Recipe(
        id = 24,
        name = "참치김치볶음",
        time = 15,

        ingredients = listOf(
            "참치",
            "김치"
        ),

        optionalIngredients = listOf(
            "대파",
            "양파"
        ),

        seasonings = listOf(
            "식용유",
            "설탕",
            "고춧가루"
        ),

        steps = listOf(
            "김치를 잘게 썬다.",
            "식용유를 두른 팬에 김치를 볶는다.",
            "참치를 넣는다.",
            "설탕과 고춧가루를 넣는다.",
            "대파와 양파가 있다면 넣고 볶아 완성한다."
        )
    ),


    // -------------------------------------------------
    // 25. 김치 햄 덮밥
    // -------------------------------------------------

    Recipe(
        id = 25,
        name = "김치 햄 덮밥",
        time = 15,

        ingredients = listOf(
            "밥",
            "김치",
            "햄"
        ),

        optionalIngredients = listOf(
            "계란",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "설탕"
        ),

        steps = listOf(
            "햄을 먹기 좋게 자른다.",
            "식용유를 두른 팬에 햄을 볶는다.",
            "김치를 넣고 함께 볶는다.",
            "진간장과 설탕으로 간을 맞춘다.",
            "밥 위에 볶은 재료를 올린다.",
            "기호에 따라 계란과 대파를 추가한다."
        )
    ),


    // -------------------------------------------------
    // 26. 두부 덮밥
    // -------------------------------------------------

    Recipe(
        id = 26,
        name = "두부 덮밥",
        time = 15,

        ingredients = listOf(
            "밥",
            "두부"
        ),

        optionalIngredients = listOf(
            "계란",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "참기름"
        ),

        steps = listOf(
            "두부를 잘게 자른다.",
            "식용유를 두른 팬에서 두부를 볶는다.",
            "진간장을 넣어 간을 한다.",
            "밥을 그릇에 담는다.",
            "두부를 밥 위에 올린다.",
            "참기름을 넣고 대파가 있다면 올린다."
        )
    ),


    // -------------------------------------------------
    // 27. 감자 덮밥
    // -------------------------------------------------

    Recipe(
        id = 27,
        name = "감자 덮밥",
        time = 20,

        ingredients = listOf(
            "밥",
            "감자"
        ),

        optionalIngredients = listOf(
            "계란",
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "설탕"
        ),

        steps = listOf(
            "감자를 잘게 자른다.",
            "식용유를 두르고 감자를 볶는다.",
            "양파가 있다면 넣고 함께 볶는다.",
            "물을 조금 넣고 익힌다.",
            "진간장과 설탕으로 간한다.",
            "밥 위에 올린다.",
            "기호에 따라 계란과 대파를 추가한다."
        )
    ),


    // -------------------------------------------------
    // 28. 햄 덮밥
    // -------------------------------------------------

    Recipe(
        id = 28,
        name = "햄 덮밥",
        time = 10,

        ingredients = listOf(
            "밥",
            "햄"
        ),

        optionalIngredients = listOf(
            "계란",
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "진간장"
        ),

        steps = listOf(
            "햄을 먹기 좋은 크기로 자른다.",
            "팬에 식용유를 두르고 햄을 굽는다.",
            "진간장을 조금 넣어 간한다.",
            "밥을 그릇에 담는다.",
            "햄을 밥 위에 올린다.",
            "기호에 따라 계란과 양파, 대파를 추가한다."
        )
    ),


    // -------------------------------------------------
    // 29. 김치 두부볶음
    // -------------------------------------------------

    Recipe(
        id = 29,
        name = "김치 두부볶음",
        time = 15,

        ingredients = listOf(
            "김치",
            "두부"
        ),

        optionalIngredients = listOf(
            "대파",
            "양파"
        ),

        seasonings = listOf(
            "식용유",
            "고춧가루",
            "참기름"
        ),

        steps = listOf(
            "두부를 먹기 좋게 자른다.",
            "식용유를 두른 팬에 김치를 볶는다.",
            "고춧가루를 넣는다.",
            "두부를 넣고 함께 볶는다.",
            "양파와 대파가 있다면 넣는다.",
            "불을 끄고 참기름을 넣는다."
        )
    ),


    // -------------------------------------------------
    // 30. 햄 두부볶음
    // -------------------------------------------------

    Recipe(
        id = 30,
        name = "햄 두부볶음",
        time = 15,

        ingredients = listOf(
            "햄",
            "두부"
        ),

        optionalIngredients = listOf(
            "양파",
            "대파"
        ),

        seasonings = listOf(
            "식용유",
            "진간장",
            "후추"
        ),

        steps = listOf(
            "햄과 두부를 먹기 좋게 자른다.",
            "식용유를 두른 팬에 햄을 볶는다.",
            "두부를 넣는다.",
            "진간장으로 간한다.",
            "양파와 대파가 있다면 넣는다.",
            "후추를 넣고 마무리한다."
        )
    )
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