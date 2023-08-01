package com.example.news.api



import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName



data class NewsDataModel(
    @SerialName("articles")
    val articles: List<Article>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int
):java.io.Serializable

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerialName("author")
    val author: String,
    @SerialName("content")
    val content: String,
    @SerialName("description")
    val description: String,
    @SerialName("publishedAt")
    val publishedAt: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("urlToImage")
    val urlToImage: String
):java.io.Serializable

data class Source(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String
):java.io.Serializable