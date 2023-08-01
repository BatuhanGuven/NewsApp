package com.example.news

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.news.api.Article

data class BottomNavItem(
    val name : String,
    val route: String,
    val icon : ImageVector
)

data class NavItem(
    val article: Article,
    val route: String
)
