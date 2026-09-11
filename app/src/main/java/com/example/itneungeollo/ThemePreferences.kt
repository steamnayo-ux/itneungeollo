
package com.example.itneungeollo

import android.content.Context

// Room까지 쓰기엔 너무 가벼운 설정값(다크모드 on/off)이라
// 안드로이드 표준 SharedPreferences로 따로 저장해요.
class ThemePreferences(context: Context) {

    private val prefs =
        context.applicationContext.getSharedPreferences(
            "theme_prefs",
            Context.MODE_PRIVATE
        )

    fun isDarkMode(): Boolean =
        prefs.getBoolean(KEY_DARK_MODE, false)

    fun setDarkMode(isDarkMode: Boolean) {
        prefs.edit()
            .putBoolean(KEY_DARK_MODE, isDarkMode)
            .apply()
    }

    companion object {
        private const val KEY_DARK_MODE = "is_dark_mode"
    }
}