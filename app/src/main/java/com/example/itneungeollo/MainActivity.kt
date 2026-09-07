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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.FlowRow
import androidx.activity.compose.BackHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch
import kotlin.random.Random

import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.shadow

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
fun App(viewModel: AppViewModel = viewModel()) {

    BackHandler(enabled = viewModel.screen != "home") {
        viewModel.goBack()
    }

    when (viewModel.screen) {

        "home" -> {

            HomeScreen(
                onStartClick = {
                    viewModel.goToIngredients()
                }
            )
        }

        "ingredients" -> {

            IngredientScreen(
                selectedIngredients = viewModel.selectedIngredients,

                onIngredientChange = { ingredients ->
                    viewModel.updateIngredients(ingredients)
                },

                onRecommendClick = {
                    viewModel.goToRecommend()
                }
            )
        }

        "recommend" -> {

            RecommendScreen(
                userIngredients = viewModel.selectedIngredients,
                onBackClick = {
                    viewModel.goBack()
                },
                onRecipeClick = { recipe ->
                    viewModel.selectRecipe(recipe)
                }
            )
        }

        "detail" -> {

            viewModel.selectedRecipe?.let { recipe ->

                RecipeDetailScreen(
                    recipe = recipe,

                    onBackClick = {
                        viewModel.goBack()
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

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            val targetX = Random.nextInt(-22, 23).toFloat()
            val targetY = Random.nextInt(-22, 23).toFloat()

            launch {
                offsetX.animateTo(
                    targetValue = targetX,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            offsetY.animateTo(
                targetValue = targetY,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF5))
            .padding(horizontal = 28.dp, vertical = 40.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // 뒤쪽 은은한 그림자 원 + 앞쪽 원형 배경 + 통통 튀는 이모지
            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .shadow(
                            elevation = 18.dp,
                            shape = androidx.compose.foundation.shape.CircleShape,
                            ambientColor = Color(0xFF6A4FB6),
                            spotColor = Color(0xFF6A4FB6)
                        )
                        .background(
                            color = Color(0xFFEDE7F6),
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )

                Text(
                    text = "🍳",
                    fontSize = 68.sp,
                    modifier = Modifier.offset(
                        x = offsetX.value.dp,
                        y = offsetY.value.dp
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "있는걸로",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D2438)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "집에 있는 재료로",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF4A4A4A),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                text = "오늘 뭐 먹지?",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6A4FB6),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "레시피 50개 · 재료 40여 가지",
                fontSize = 13.sp,
                color = Color(0xFFA69BC7)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = Color(0xFF6A4FB6),
                        spotColor = Color(0xFF6A4FB6)
                    ),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A4FB6)
                ),

                onClick = onStartClick
            ) {
                Text(
                    text = "재료 고르기 🍚",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


// =====================================================
// 재료 선택 화면
// =====================================================

@Composable
fun IngredientScreen(
    selectedIngredients: Set<String>,
    onIngredientChange: (Set<String>) -> Unit,
    onRecommendClick: () -> Unit
) {
    var ingredientSearchText by remember {
        mutableStateOf("")
    }

    val ingredientCategories = listOf(
        IngredientCategory.VEGETABLE to "🥬 채소",
        IngredientCategory.MEAT to "🥩 육류",
        IngredientCategory.SEAFOOD to "🐟 수산물",
        IngredientCategory.DAIRY_EGG to "🥚 계란·유제품",
        IngredientCategory.FRUIT to "🥑 과일",
        IngredientCategory.GRAIN_NOODLE to "🌾 곡류·면",
        IngredientCategory.PROCESSED to "🥫 가공식품"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "있는걸로",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "집에 있는 재료를 선택해주세요.",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // 재료 검색
        OutlinedTextField(
            value = ingredientSearchText,
            onValueChange = {
                ingredientSearchText = it
            },
            label = {
                Text("재료 검색")
            },
            placeholder = {
                Text("예: 계란, 양파, 감자")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )



        // 선택된 재료
        if (selectedIngredients.isNotEmpty()) {

            Text(
                text = "선택한 재료",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedIngredients.forEach { ingredient ->

                    Button(
                        onClick = {
                            onIngredientChange(
                                selectedIngredients - ingredient
                            )
                        }
                    ) {
                        Text("✓ $ingredient")
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        // 재료 카테고리
        ingredientCategories.forEach { (category, categoryName) ->

            val filteredIngredients =
                ingredients
                    .filter {
                        it.category == category
                    }
                    .filter { ingredient ->

                        val searchText =
                            ingredientSearchText.trim()

                        searchText.isEmpty() ||
                                ingredient.name.contains(
                                    searchText,
                                    ignoreCase = true
                                ) ||
                                ingredient.aliases.any {
                                    it.contains(
                                        searchText,
                                        ignoreCase = true
                                    )
                                }
                    }

            if (filteredIngredients.isNotEmpty()) {

                Text(
                    text = categoryName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                filteredIngredients
                    .chunked(3)
                    .forEach { rowIngredients ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement =
                                Arrangement.SpaceEvenly
                        ) {

                            rowIngredients.forEach { ingredient ->

                                val isSelected =
                                    ingredient.name in selectedIngredients

                                Button(
                                    onClick = {

                                        onIngredientChange(
                                            if (isSelected) {
                                                selectedIngredients -
                                                        ingredient.name
                                            } else {
                                                selectedIngredients +
                                                        ingredient.name
                                            }
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor =
                                            if (isSelected) {
                                                Color(0xFF6A4FB6) // 선택됨: 진한 보라
                                            } else {
                                                Color(0xFFEDE7F6) // 선택 안 됨: 연한 회보라
                                            },
                                        contentColor =
                                            if (isSelected) {
                                                Color.White
                                            } else {
                                                Color(0xFF4A4A4A)
                                            }
                                    )
                                ) {
                                    Text(
                                        text =
                                            if (isSelected) {
                                                "✓ ${ingredient.name}"
                                            } else {
                                                ingredient.name
                                            },
                                        fontSize = 15.sp,
                                        fontWeight =
                                            if (isSelected) {
                                                FontWeight.Bold
                                            } else {
                                                FontWeight.Normal
                                            }
                                    )
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                    }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // 검색 결과가 없을 때 직접 추가
        val searchText = ingredientSearchText.trim()

        if (
            searchText.isNotEmpty() &&
            ingredients.none { ingredient ->
                ingredient.name.contains(
                    searchText,
                    ignoreCase = true
                ) ||
                        ingredient.aliases.any {
                            it.contains(
                                searchText,
                                ignoreCase = true
                            )
                        }
            }
        ) {

            Text(
                text = "검색 결과가 없어요 😢",
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Button(
                onClick = {

                    val normalizedIngredient =
                        normalizeIngredient(searchText)

                    onIngredientChange(
                        selectedIngredients + normalizedIngredient
                    )

                    ingredientSearchText = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "+ $searchText 추가",
                    fontSize = 17.sp
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        // 레시피 추천 버튼
        Button(
            onClick = onRecommendClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "레시피 추천받기",
                fontSize = 18.sp
            )
        }
    }
}


// =====================================================
// 재료 이름 정리
// =====================================================

fun normalizeIngredient(
    input: String
): String {

    val normalizedInput =
        input
            .trim()
            .replace(" ", "")

    val ingredient =
        ingredients.firstOrNull { item ->

            item.name.replace(" ", "") == normalizedInput ||
                    item.aliases.any { alias ->
                        alias.replace(" ", "") == normalizedInput
                    }
        }

    return ingredient?.name ?: normalizedInput
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
    val context = androidx.compose.ui.platform.LocalContext.current

    val recipes = remember {
        loadRecipesFromAssets(context)
    }

    val recommendedRecipes =
        recommendRecipes(
            userIngredients = userIngredients,
            recipes = recipes
        )

    // 바로 만들 수 있는 레시피
    val readyRecipes =
        recommendedRecipes.filter {
            it.missingRequired.isEmpty()
        }

    // 필수 재료가 1개만 부족한 레시피
    val almostRecipes =
        recommendedRecipes.filter {
            it.missingRequired.size == 1
        }

    // 필수 재료가 2개 이상 부족한 레시피
    val candidateRecipes =
        recommendedRecipes.filter {
            it.missingRequired.size >= 2
        }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF5))
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        // 뒤로가기
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
            text = "오늘은 이걸로 먹어요 🍳",
            fontSize = 29.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "내 재료: ${
                userIngredients.joinToString(", ")
            }",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )


        // =================================================
        // 바로 만들 수 있어요
        // =================================================

        if (readyRecipes.isNotEmpty()) {

            Text(
                text = "🔥 지금 바로 만들 수 있어요",
                fontSize = 23.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            readyRecipes.forEachIndexed { index, recommendation ->

                RecipeCard(
                    rank = index + 1,
                    recommendation = recommendation,
                    userIngredients = userIngredients,
                    onRecipeClick = {
                        onRecipeClick(recommendation.recipe)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }


        // =================================================
        // 거의 다 있어요
        // =================================================

        if (almostRecipes.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "👍 재료가 거의 있어요",
                fontSize = 23.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            almostRecipes.forEachIndexed { index, recommendation ->

                RecipeCard(
                    rank = index + 1,
                    recommendation = recommendation,
                    userIngredients = userIngredients,
                    onRecipeClick = {
                        onRecipeClick(recommendation.recipe)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }


        // =================================================
        // 이런 메뉴도 있어요
        // =================================================

        if (candidateRecipes.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "💡 이런 메뉴도 있어요",
                fontSize = 23.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            candidateRecipes.forEachIndexed { index, recommendation ->

                RecipeCard(
                    rank = index + 1,
                    recommendation = recommendation,
                    userIngredients = userIngredients,
                    onRecipeClick = {
                        onRecipeClick(recommendation.recipe)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }


        // =================================================
        // 아무것도 없을 때
        // =================================================

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
                        "비슷한 재료로 만들 수 있는\n레시피를 찾지 못했어요 😢",

                    fontSize = 19.sp,

                    modifier = Modifier.padding(20.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}


// =====================================================
// 레시피 카드
// =====================================================

@Composable
fun RecipeCard(
    rank: Int,
    recommendation: RecipeRecommendation,
    userIngredients: Set<String>,
    onRecipeClick: () -> Unit
) {

    val recipe = recommendation.recipe

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

            // 레시피 이름
            Text(
                text = "$rank. ${recipe.emoji} ${recipe.name}",
                fontSize = 24.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // 필수 재료 보유 현황
            Text(
                text =
                    "필수 재료  " +
                            "${recommendation.matchedRequired} / " +
                            "${recipe.ingredients.size} ✅",

                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )


            // 부족한 재료
            if (recommendation.missingRequired.isNotEmpty()) {

                Text(
                    text =
                        "⚠️ 부족한 재료: " +
                                recommendation.missingRequired.joinToString(", "),

                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD9534F) // 경고용 붉은 계열
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )
            }


            // 선택 재료
            Text(
                text =
                    "내 재료 활용  " +
                            "${recommendation.matchedRequired + recommendation.matchedOptional}개 ⭐",

                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )


            // 조리 시간
            Text(
                text = "⏱ ${recipe.time}분",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )


            // 상세 레시피 버튼
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
                    text = "${recipe.emoji} ${recipe.name}",
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