package com.example.itneungeollo.ui.theme

import androidx.compose.ui.graphics.Color

// 앱 전체에서 화면마다 쓰던 하드코딩 색상들을 한곳에 모아 라이트/다크 두 세트로 분리했어요.
// 브랜드 포인트 컬러(보라색)는 두 모드에서 동일하게 유지합니다.
data class AppColors(
    val background: Color,       // 화면 배경 (기존 크림색)
    val cardBackground: Color,   // 카드 배경 (기존 흰색)
    val titleText: Color,        // 홈 화면 큰 타이틀
    val textPrimary: Color,      // 본문 텍스트
    val textSecondary: Color,    // 보조 라벨 텍스트
    val subtitleText: Color,     // 홈 화면 캡션 (연보라)
    val accent: Color,           // 브랜드 포인트 컬러 (진보라)
    val accentSoft: Color        // 선택 안 된 칩/버튼 배경 (연보라)
)

fun lightAppColors() = AppColors(
    background = Color(0xFFFFFBF5),
    cardBackground = Color.White,
    titleText = Color(0xFF2D2438),
    textPrimary = Color(0xFF4A4A4A),
    textSecondary = Color(0xFF8A8A8A),
    subtitleText = Color(0xFFA69BC7),
    accent = Color(0xFF6A4FB6),
    accentSoft = Color(0xFFEDE7F6)
)

fun darkAppColors() = AppColors(
    background = Color(0xFF1C1A22),
    cardBackground = Color(0xFF2A2733),
    titleText = Color(0xFFF3EEFB),
    textPrimary = Color(0xFFE4E0EA),
    textSecondary = Color(0xFFB0AABB),
    subtitleText = Color(0xFFC3B4E6),
    accent = Color(0xFF8B72D6), // 어두운 배경에서 대비를 위해 살짝 밝힌 보라
    accentSoft = Color(0xFF3A3548)
)