package com.example.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.news.api.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM article")
    fun getAllNews(): Flow<List<Article>>
    @Insert
     suspend fun insertNews(article: Article)
}