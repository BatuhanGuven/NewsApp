package com.example.news.view


import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.news.viewModel.MyViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = MyViewModel(applicationContext)
        super.onCreate(savedInstanceState)
        setContent {
           MainScreen(viewModel = viewModel)
        }
    }

}