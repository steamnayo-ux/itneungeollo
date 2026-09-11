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
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.stateDescription

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.safeDrawing
import com.example.itneungeollo.ui.theme.AppColors
import com.example.itneungeollo.ui.theme.lightAppColors
import com.example.itneungeollo.ui.theme.darkAppColors

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

    val appColors =
        if (viewModel.isDarkMode) darkAppColors() else lightAppColors()

    com.example.itneungeollo.ui.theme.ItneungeolloTheme(
        darkTheme = viewModel.isDarkMode,
        dynamicColor = false // 다크모드 토글이 시스템 색상에 묻히지 않도록 꺼둠
    ) {

        androidx.compose.material3.Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            color = appColors.background,
            contentColor = appColors.textPrimary
        ) {

            when (viewModel.screen) {

                "home" -> {

                    HomeScreen(
                        colors = appColors,
                        favoriteCount = viewModel.userRecipeDataMap.values.count { it.isFavorite },
                        isDarkMode = viewModel.isDarkMode,
                        onStartClick = {
                            viewModel.goToIngredients()
                        },
                        onFavoritesClick = {
                            viewModel.goToFavorites()
                        },
                        onRecentClick = {
                            viewModel.goToRecent()
                        },
                        onToggleDarkMode = {
                            viewModel.toggleDarkMode()
                        }
                    )
                }

                "ingredients" -> {

                    IngredientScreen(
                        colors = appColors,
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
                        colors = appColors,
                        userIngredients = viewModel.selectedIngredients,
                        onBackClick = {
                            viewModel.goBack()
                        },
                        onRecipeClick = { recipe ->
                            viewModel.selectRecipe(recipe)
                        }
                    )
                }

                "favorites" -> {

                    FavoritesScreen(
                        colors = appColors,
                        favoriteRecipeIds = viewModel.userRecipeDataMap.values
                            .filter { it.isFavorite }
                            .map { it.recipeId }
                            .toSet(),
                        getRating = { recipeId ->
                            viewModel.getRating(recipeId)
                        },
                        onBackClick = {
                            viewModel.goBack()
                        },
                        onRecipeClick = { recipe ->
                            viewModel.selectRecipe(recipe)
                        }
                    )
                }

                "recent" -> {

                    RecentScreen(
                        colors = appColors,
                        recentRecipeIdsOrdered = viewModel.userRecipeDataMap.values
                            .filter { it.lastViewedAt != null }
                            .sortedByDescending { it.lastViewedAt }
                            .map { it.recipeId },
                        getRating = { recipeId ->
                            viewModel.getRating(recipeId)
                        },
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
                            colors = appColors,
                            recipe = recipe,
                            isFavorite = viewModel.isFavorite(recipe.id),
                            rating = viewModel.getRating(recipe.id),
                            memo = viewModel.getMemo(recipe.id),
                            onToggleFavorite = {
                                viewModel.toggleFavorite(recipe.id)
                            },
                            onRatingChange = { star ->
                                viewModel.setRating(recipe.id, star)
                            },
                            onMemoChange = { newMemo ->
                                viewModel.setMemo(recipe.id, newMemo)
                            },

                            onBackClick = {
                                viewModel.goBack()
                            }
                        )
                    }
                }
            }
        }
    }
}


// =====================================================
// 뒤로가기 버튼 (작은 원형 아이콘 스타일)
// =====================================================

@Composable
fun BackButton(
    colors: AppColors,
    onClick: () -> Unit
) {
    androidx.compose.material3.IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .background(
                color = colors.accentSoft,
                shape = androidx.compose.foundation.shape.CircleShape
            )
    ) {
        Text(
            text = "←",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colors.accent
        )
    }
}


// =====================================================
// 홈 화면
// =====================================================

@Composable
fun HomeScreen(
    colors: AppColors,
    favoriteCount: Int,
    isDarkMode: Boolean,
    onStartClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onRecentClick: () -> Unit,
    onToggleDarkMode: () -> Unit
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
            .background(colors.background)
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
                            ambientColor = colors.accent,
                            spotColor = colors.accent
                        )
                        .background(
                            color = colors.accentSoft,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )

                Text(
                    text = "🍳",
                    fontSize = 68.sp,
                    modifier = Modifier
                        .offset(x = offsetX.value.dp, y = offsetY.value.dp)
                        .semantics { invisibleToUser() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "있는걸로",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = colors.titleText
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "집에 있는 재료로",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = colors.textPrimary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                text = "오늘 뭐 먹지?",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = colors.accent,
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
                color = colors.subtitleText
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = colors.accent,
                        spotColor = colors.accent
                    ),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.accent
                ),

                onClick = onStartClick
            ) {
                Text(
                    text = "재료 고르기 🍚",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                TextButton(
                    onClick = onFavoritesClick
                ) {
                    Text(
                        text = if (favoriteCount > 0) {
                            "★ 즐겨찾기 ($favoriteCount)"
                        } else {
                            "★ 즐겨찾기"
                        },
                        fontSize = 15.sp,
                        color = colors.accent
                    )
                }

                TextButton(
                    onClick = onRecentClick
                ) {
                    Text(
                        text = "🕐 최근 본",
                        fontSize = 15.sp,
                        color = colors.accent
                    )
                }

                TextButton(
                    onClick = onToggleDarkMode
                ) {
                    Text(
                        text = if (isDarkMode) "☀️ 라이트 모드" else "🌙 다크 모드",
                        fontSize = 15.sp,
                        color = colors.accent
                    )
                }
            }
        }
    }
}


// =====================================================
// 재료 선택 화면
// =====================================================

@Composable
fun IngredientScreen(
    colors: AppColors,
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
                                                colors.accent // 선택됨: 진한 보라
                                            } else {
                                                colors.accentSoft // 선택 안 됨: 연한 회보라
                                            },
                                        contentColor =
                                            if (isSelected) {
                                                Color.White
                                            } else {
                                                colors.textPrimary
                                            }
                                    ),
                                    modifier = Modifier.semantics {
                                        stateDescription = if (isSelected) "선택됨" else "선택 안 됨"
                                    }

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
    colors: AppColors,
    userIngredients: Set<String>,
    onBackClick: () -> Unit,
    onRecipeClick: (Recipe) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val recipes = remember {
        loadRecipesFromAssets(context)
    }

    var recipeSearchText by remember {
        mutableStateOf("")
    }

    // 조리시간 필터
    val timeFilterOptions = listOf("전체", "15분 이내", "30분 이내", "30분 초과")

    var selectedTimeFilter by remember {
        mutableStateOf("전체")
    }

    val recommendedRecipes =
        recommendRecipes(
            userIngredients = userIngredients,
            recipes = recipes
        ).filter { recommendation ->
            val searchText = recipeSearchText.trim()
            searchText.isEmpty() ||
                    recommendation.recipe.name.contains(
                        searchText,
                        ignoreCase = true
                    )
        }.filter { recommendation ->
            when (selectedTimeFilter) {
                "15분 이내" -> recommendation.recipe.time <= 15
                "30분 이내" -> recommendation.recipe.time <= 30
                "30분 초과" -> recommendation.recipe.time > 30
                else -> true // "전체"
            }
        }

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
            .background(colors.background)
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        // 뒤로가기
        BackButton(
            colors = colors,
            onClick = onBackClick
        )

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
            modifier = Modifier.height(16.dp)
        )

        // 레시피 이름 검색
        OutlinedTextField(
            value = recipeSearchText,
            onValueChange = {
                recipeSearchText = it
            },
            label = {
                Text("레시피 이름 검색")
            },
            placeholder = {
                Text("예: 김치찌개")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // 조리시간 필터
        Text(
            text = "조리시간",
            fontSize = 14.sp,
            color = colors.textSecondary
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            timeFilterOptions.forEach { option ->

                val isSelected = option == selectedTimeFilter

                Button(
                    onClick = {
                        selectedTimeFilter = option
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (isSelected) {
                                colors.accent
                            } else {
                                colors.accentSoft
                            },
                        contentColor =
                            if (isSelected) {
                                Color.White
                            } else {
                                colors.textPrimary
                            }
                    ),
                    modifier = Modifier.semantics {
                        stateDescription = if (isSelected) "선택됨" else "선택 안 됨"
                    }
                ) {
                    Text(
                        text = option,
                        fontSize = 14.sp,
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
            modifier = Modifier.height(25.dp)
        )

        if (
            recommendedRecipes.isEmpty() &&
            (recipeSearchText.isNotBlank() || selectedTimeFilter != "전체")
        ) {
            Text(
                text =
                    if (recipeSearchText.isNotBlank()) {
                        "\"$recipeSearchText\"와(과) 일치하는 레시피가 없어요 😢"
                    } else {
                        "조건에 맞는 레시피가 없어요 😢"
                    },
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }


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
                    colors = colors,
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
                    colors = colors,
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
                    colors = colors,
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
                    containerColor = colors.cardBackground
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
// 즐겨찾기 화면
// =====================================================

@Composable
fun FavoritesScreen(
    colors: AppColors,
    favoriteRecipeIds: Set<Int>,
    getRating: (Int) -> Int?,
    onBackClick: () -> Unit,
    onRecipeClick: (Recipe) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val recipes = remember {
        loadRecipesFromAssets(context)
    }

    val favoriteRecipes =
        recipes.filter { it.id in favoriteRecipeIds }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        BackButton(
            colors = colors,
            onClick = onBackClick
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "★ 즐겨찾기",
            fontSize = 29.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (favoriteRecipes.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                colors = CardDefaults.cardColors(
                    containerColor = colors.cardBackground
                )
            ) {

                Text(
                    text =
                        "아직 즐겨찾기한 레시피가 없어요.\n" +
                                "레시피 상세 화면에서 ☆ 버튼을 눌러보세요!",

                    fontSize = 17.sp,

                    modifier = Modifier.padding(20.dp)
                )
            }

        } else {

            favoriteRecipes.forEach { recipe ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = colors.cardBackground
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {

                        Text(
                            text = "${recipe.emoji} ${recipe.name}",
                            fontSize = 22.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "⏱ ${recipe.time}분",
                            fontSize = 15.sp
                        )

                        val rating = getRating(recipe.id)

                        if (rating != null) {

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "★".repeat(rating) + "☆".repeat(5 - rating),
                                fontSize = 15.sp,
                                color = colors.accent
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onRecipeClick(recipe)
                            }
                        ) {
                            Text(
                                text = "레시피 보기"
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}


// =====================================================
// 최근 본 레시피 화면
// =====================================================

@Composable
fun RecentScreen(
    colors: AppColors,
    recentRecipeIdsOrdered: List<Int>,
    getRating: (Int) -> Int?,
    onBackClick: () -> Unit,
    onRecipeClick: (Recipe) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val recipes = remember {
        loadRecipesFromAssets(context)
    }

    val recipeById = remember(recipes) {
        recipes.associateBy { it.id }
    }

    // 순서(최근 본 순)를 그대로 유지하면서 레시피 객체로 변환
    val recentRecipes =
        recentRecipeIdsOrdered.mapNotNull { recipeById[it] }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        BackButton(
            colors = colors,
            onClick = onBackClick
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "🕐 최근 본 레시피",
            fontSize = 29.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (recentRecipes.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                colors = CardDefaults.cardColors(
                    containerColor = colors.cardBackground
                )
            ) {

                Text(
                    text = "아직 본 레시피가 없어요.\n레시피를 눌러서 확인해보세요!",

                    fontSize = 17.sp,

                    modifier = Modifier.padding(20.dp)
                )
            }

        } else {

            recentRecipes.forEach { recipe ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = colors.cardBackground
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {

                        Text(
                            text = "${recipe.emoji} ${recipe.name}",
                            fontSize = 22.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "⏱ ${recipe.time}분",
                            fontSize = 15.sp
                        )

                        val rating = getRating(recipe.id)

                        if (rating != null) {

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "★".repeat(rating) + "☆".repeat(5 - rating),
                                fontSize = 15.sp,
                                color = colors.accent
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onRecipeClick(recipe)
                            }
                        ) {
                            Text(
                                text = "레시피 보기"
                            )
                        }
                    }
                }
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
    colors: AppColors,
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
            containerColor = colors.cardBackground
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
    colors: AppColors,
    recipe: Recipe,
    isFavorite: Boolean,
    rating: Int?,
    memo: String?,
    onToggleFavorite: () -> Unit,
    onRatingChange: (Int) -> Unit,
    onMemoChange: (String) -> Unit,
    onBackClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    // DB 저장은 비동기라 왕복 지연 때문에 입력창이 끊기지 않도록,
    // 레시피별로 로컬 상태를 따로 들고 타이핑은 즉시 반영 + 저장은 콜백으로 위임
    var memoText by remember(recipe.id) {
        mutableStateOf(memo ?: "")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        BackButton(
            colors = colors,
            onClick = onBackClick
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp),

            colors = CardDefaults.cardColors(
                containerColor = colors.cardBackground
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "${recipe.emoji} ${recipe.name}",
                        fontSize = 30.sp
                    )

                    Button(
                        onClick = onToggleFavorite
                    ) {
                        Text(
                            text = if (isFavorite) "★ 즐겨찾기" else "☆ 즐겨찾기"
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        "⏱ 조리 시간: ${recipe.time}분",

                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // 별점
                Row {
                    (1..5).forEach { star ->
                        Text(
                            text = if ((rating ?: 0) >= star) "★" else "☆",
                            fontSize = 26.sp,
                            modifier = Modifier.clickable {
                                onRatingChange(star)
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // 메모
                OutlinedTextField(
                    value = memoText,
                    onValueChange = { newText ->
                        memoText = newText
                        onMemoChange(newText)
                    },
                    label = {
                        Text("메모")
                    },
                    placeholder = {
                        Text("예: 소금 반 스푼만 넣기, 다음엔 양파 더 넣기")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
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
                    containerColor = colors.cardBackground
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