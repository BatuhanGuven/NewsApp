package com.example.news.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApiService {
    @GET("everything?q=apple&from=2023-07-21&to=2023-07-21&sortBy=popularity&apiKey=bf9e3bfbc5644ff2b32cef15b00e8a6d")
    fun getData(
    ): Call<NewsDataModel>
    @GET("everything?q=apple&from=2023-07-21&to=2023-07-21&sortBy=popularity&apiKey=bf9e3bfbc5644ff2b32cef15b00e8a6d")
    fun getFilteredData(
    ): Call<NewsDataModel>
}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://newsapi.org/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: MyApiService = retrofit.create(MyApiService::class.java)
