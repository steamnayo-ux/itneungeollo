package com.example.itneungeollo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserRecipeData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userRecipeDataDao(): UserRecipeDataDao

    companion object {

        // 앱 전체에서 DB 인스턴스를 하나만 쓰도록 싱글턴으로 관리
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "itneungeollo.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}