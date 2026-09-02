package com.example.itneungeollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}


// =====================================================
// 앱 화면 관리
// =====================================================

@Composable
fun App() {

    var screen by remember {
        mutableStateOf("home")
    }

    var selectedIngredients by remember {
        mutableStateOf(setOf<String>())
    }

    var selectedRecipe by remember {
        mutableStateOf<Recipe?>(null)
    }

    when (screen) {

        "home" -> {

            HomeScreen(
                onStartClick = {
                    screen = "ingredients"
                }
            )
        }

        "ingredients" -> {

            IngredientScreen(
                onRecommendClick = { ingredients ->

                    selectedIngredients = ingredients

                    screen = "recommend"
                }
            )
        }

        "recommend" -> {

            RecommendScreen(
                userIngredients = selectedIngredients,

                onBackClick = {
                    screen = "ingredients"
                },

                onRecipeClick = { recipe ->

                    selectedRecipe = recipe

                    screen = "detail"
                }
            )
        }

        "detail" -> {

            selectedRecipe?.let { recipe ->

                RecipeDetailScreen(
                    recipe = recipe,

                    onBackClick = {
                        screen = "recommend"
                    }
                )
            }
        }
    }
}


// =====================================================
// 홈 화면
// =====================================================

@Composable
fun HomeScreen(
    onStartClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF5))
            .padding(30.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "있는걸로 🍳",
            fontSize = 38.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "집에 있는 재료로\n오늘 뭐 먹지?",
            fontSize = 23.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "없는 재료는 찾지 말고,\n있는 걸로 먹자!",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Button(
            modifier = Modifier.fillMaxWidth(),

            onClick = onStartClick
        ) {

            Text(
                text = "재료 고르기 🍚",
                fontSize = 18.sp
            )
        }
    }
}


// =====================================================
// 재료 선택 화면
// =====================================================

@Composable
fun IngredientScreen(
    onRecommendClick: (Set<String>) -> Unit
) {

    // 표시 이름 / 실제 저장 이름
    val ingredients = listOf(
        "🥚 계란" to "계란",
        "🍚 밥" to "밥",
        "🥬 김치" to "김치",
        "🧅 양파" to "양파",
        "🌱 대파" to "대파",
        "🧈 두부" to "두부",
        "🍖 햄" to "햄",
        "🐟 참치" to "참치",
        "🥔 감자" to "감자"
    )

    var selectedIngredients by remember {
        mutableStateOf(setOf<String>())
    }

    var inputText by remember {
        mutableStateOf("")
    }

    // 기본 양념 펼침 여부
    var showSeasonings by remember {
        mutableStateOf(false)
    }

    // 화면 스크롤
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF5))
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        // =================================================
        // 제목
        // =================================================

        Text(
            text = "뭐가 남았나요? 🥕",
            fontSize = 29.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "집에 있는 재료를 골라주세요.",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )


        // =================================================
        // 기본 재료 선택
        // =================================================

        ingredients.chunked(3).forEach { rowIngredients ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),

                horizontalArrangement =
                    Arrangement.SpaceEvenly
            ) {

                rowIngredients.forEach { (displayName, ingredientName) ->

                    val isSelected =
                        ingredientName in selectedIngredients

                    Button(
                        onClick = {

                            selectedIngredients =
                                if (isSelected) {

                                    selectedIngredients -
                                            ingredientName

                                } else {

                                    selectedIngredients +
                                            ingredientName
                                }
                        }
                    ) {

                        Text(
                            text =
                                if (isSelected) {
                                    "✓ $ingredientName"
                                } else {
                                    displayName
                                },

                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }


        Spacer(
            modifier = Modifier.height(15.dp)
        )


        // =================================================
        // 기본 양념
        // =================================================

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(16.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {
                        showSeasonings = !showSeasonings
                    }
                ) {

                    Text(
                        text =
                            if (showSeasonings) {
                                "기본 양념 🧂  ▲"
                            } else {
                                "기본 양념 🧂  ▼"
                            },

                        fontSize = 17.sp
                    )
                }


                if (showSeasonings) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 20.dp,
                                end = 20.dp,
                                bottom = 15.dp
                            )
                    ) {

                        Text(
                            text =
                                "기본 양념은 구비되어 있다고 가정합니다.",
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        basicSeasonings.forEach { seasoning ->

                            Text(
                                text = "✓ $seasoning",
                                fontSize = 16.sp
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )
                        }
                    }
                }
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =================================================
        // 직접 입력
        // =================================================

        Text(
            text = "직접 입력",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )


        OutlinedTextField(

            value = inputText,

            onValueChange = {
                inputText = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("예: 계란, 밥, 김치")
            },

            singleLine = true
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        Button(
            modifier = Modifier.fillMaxWidth(),

            onClick = {

                val newIngredients =
                    inputText
                        .split(",")
                        .map {
                            normalizeIngredient(it)
                        }
                        .filter {
                            it.isNotEmpty()
                        }
                        .toSet()

                selectedIngredients =
                    selectedIngredients + newIngredients

                inputText = ""
            }
        ) {

            Text("재료 추가")
        }


        Spacer(
            modifier = Modifier.height(25.dp)
        )


        // =================================================
        // 내가 가진 재료
        // =================================================

        Text(
            text = "내가 가진 재료",
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )


        if (selectedIngredients.isEmpty()) {

            Text(
                text = "재료를 선택해주세요 👆",
                fontSize = 17.sp
            )

        } else {

            selectedIngredients.forEach { ingredient ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),

                    shape = RoundedCornerShape(12.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = "✅ $ingredient",
                            fontSize = 17.sp
                        )

                        Button(
                            onClick = {

                                selectedIngredients =
                                    selectedIngredients - ingredient
                            }
                        ) {

                            Text("삭제")
                        }
                    }
                }
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =================================================
        // 추천 버튼
        // =================================================

        Button(
            modifier = Modifier.fillMaxWidth(),

            onClick = {

                onRecommendClick(
                    selectedIngredients
                )
            }
        ) {

            Text(
                text = "이걸로 뭐 먹지? 🍳",
                fontSize = 18.sp
            )
        }


        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}


// =====================================================
// 재료 이름 정리
// =====================================================

fun normalizeIngredient(
    input: String
): String {

    val ingredient =
        input
            .trim()
            .replace(" ", "")

    return when {

        ingredient.contains("달걀") -> "계란"

        ingredient.contains("계란") -> "계란"

        ingredient.contains("밥") -> "밥"

        ingredient.contains("김치") -> "김치"

        ingredient.contains("양파") -> "양파"

        ingredient.contains("대파") -> "대파"

        ingredient.contains("두부") -> "두부"

        ingredient.contains("햄") -> "햄"

        ingredient.contains("참치") -> "참치"

        ingredient.contains("감자") -> "감자"

        else -> ingredient
    }
}


// =====================================================
// 추천 결과 화면
// =====================================================

@Composable
fun RecommendScreen(
    userIngredients: Set<String>,
    onBackClick: () -> Unit,
    onRecipeClick: (Recipe) -> Unit
) {

    val recommendedRecipes =
        recommendRecipes(
            userIngredients = userIngredients,
            recipes = recipes
        )

    val recipesPerPage = 3

    var currentPage by remember {
        mutableStateOf(0)
    }

    val startIndex =
        currentPage * recipesPerPage

    val endIndex =
        minOf(
            startIndex + recipesPerPage,
            recommendedRecipes.size
        )

    val currentRecipes =
        if (startIndex < recommendedRecipes.size) {

            recommendedRecipes.subList(
                startIndex,
                endIndex
            )

        } else {

            emptyList()
        }


    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF5))
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        Button(
            onClick = onBackClick
        ) {

            Text(
                text = "← 재료 다시 고르기"
            )
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        Text(
            text = "지금 만들 수 있어요 🍳",
            fontSize = 29.sp
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Text(
            text =
                "내 재료: ${
                    userIngredients.joinToString(", ")
                }",

            fontSize = 16.sp
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        if (recommendedRecipes.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Text(
                    text =
                        "지금 가진 재료로 만들 수 있는\n레시피가 없어요 😢",

                    fontSize = 19.sp,

                    modifier = Modifier.padding(20.dp)
                )
            }

        } else {

            currentRecipes.forEachIndexed { index, recipe ->

                RecipeCard(
                    rank = startIndex + index + 1,
                    recipe = recipe,
                    userIngredients = userIngredients,

                    onRecipeClick = {
                        onRecipeClick(recipe)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }


            if (endIndex < recommendedRecipes.size) {

                Button(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {
                        currentPage++
                    }
                ) {

                    Text(
                        text = "다른 메뉴 보여줘 🍽️"
                    )
                }
            }
        }
    }
}


// =====================================================
// 레시피 카드
// =====================================================

@Composable
fun RecipeCard(
    rank: Int,
    recipe: Recipe,
    userIngredients: Set<String>,
    onRecipeClick: () -> Unit
) {

    val requiredCount =
        recipe.ingredients.count { ingredient ->

            ingredient in userIngredients
        }


    val totalRequired =
        recipe.ingredients.size


    val optionalCount =
        recipe.optionalIngredients.count { ingredient ->

            ingredient in userIngredients
        }


    val totalMatch =
        requiredCount + optionalCount


    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Text(
                text = "$rank. ${recipe.name}",
                fontSize = 24.sp
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            Text(
                text =
                    "필수 재료  " +
                            "$requiredCount / $totalRequired ✅",

                fontSize = 16.sp
            )


            Spacer(
                modifier = Modifier.height(5.dp)
            )


            Text(
                text =
                    "내 재료 활용  " +
                            "${totalMatch}개 ⭐",

                fontSize = 16.sp
            )


            Spacer(
                modifier = Modifier.height(5.dp)
            )


            Text(
                text = "⏱ ${recipe.time}분",
                fontSize = 16.sp
            )


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            Button(
                modifier = Modifier.fillMaxWidth(),

                onClick = onRecipeClick
            ) {

                Text(
                    text = "레시피 보기"
                )
            }
        }
    }
}


// =====================================================
// 레시피 상세 화면
// =====================================================

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBackClick: () -> Unit
) {

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF5))
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        Button(
            onClick = onBackClick
        ) {

            Text(
                text = "← 추천 목록으로"
            )
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Text(
                    text = "🍳 ${recipe.name}",
                    fontSize = 30.sp
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        "⏱ 조리 시간: ${recipe.time}분",

                    fontSize = 18.sp
                )
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =================================================
        // 필수 재료
        // =================================================

        Text(
            text = "필수 재료",
            fontSize = 23.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )


        recipe.ingredients.forEach { ingredient ->

            Text(
                text = "✓ $ingredient",
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =================================================
        // 있으면 좋은 재료
        // =================================================

        Text(
            text = "있으면 좋은 재료",
            fontSize = 23.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )


        if (recipe.optionalIngredients.isEmpty()) {

            Text(
                text = "없어요.",
                fontSize = 17.sp
            )

        } else {

            recipe.optionalIngredients.forEach { ingredient ->

                Text(
                    text = "○ $ingredient",
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =================================================
        // 기본 양념
        // =================================================

        Text(
            text = "기본 양념 🧂",
            fontSize = 23.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )


        if (recipe.seasonings.isEmpty()) {

            Text(
                text = "사용하는 기본 양념이 없어요.",
                fontSize = 17.sp
            )

        } else {

            recipe.seasonings.forEach { seasoning ->

                Text(
                    text = "✓ $seasoning",
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =================================================
        // 만드는 방법
        // =================================================

        Text(
            text = "만드는 방법",
            fontSize = 23.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )


        recipe.steps.forEachIndexed { index, step ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),

                shape = RoundedCornerShape(12.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Text(
                    text =
                        "${index + 1}. $step",

                    fontSize = 17.sp,

                    modifier = Modifier.padding(14.dp)
                )
            }
        }


        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}