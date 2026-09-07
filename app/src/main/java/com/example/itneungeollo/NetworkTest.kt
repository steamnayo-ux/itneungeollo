package com.example.itneungeollo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

suspend fun fetchRandomAdvice(): String {

    return withContext(Dispatchers.IO) {

        try {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://api.adviceslip.com/advice")
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@withContext "응답이 없어요"

            val json = org.json.JSONObject(body)
            json.getJSONObject("slip").getString("advice")

        } catch (e: Exception) {
            "네트워크 오류: ${e.message}"
        }
    }
}

