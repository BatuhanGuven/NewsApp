package com.example.news


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.api.Article


@Database(entities = [Article::class], version = 1)
abstract class MyAppDataBase:RoomDatabase() {
    abstract fun newsDao(): NewsDao
}