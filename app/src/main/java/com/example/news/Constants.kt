package com.example.news

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import com.example.news.api.Article

val bottomNavItems = listOf<BottomNavItem>(
    BottomNavItem("Home","home",Icons.Default.Home),
    BottomNavItem("Favorite","favorite",Icons.Default.Favorite)
)
val navItem = NavItem(Article(0,"","","","","","",""),"detay")