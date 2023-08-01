package com.example.news.viewModel


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.news.MyAppDataBase
import com.example.news.api.Article
import com.example.news.api.NewsDataModel
import com.example.news.api.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyViewModel(context: Context):ViewModel(){
    var _dataApi = mutableStateOf(emptyList<Article>())
    var searchResults = mutableStateOf(emptyList<Article>())
    var _dataDb = mutableStateOf(emptyList<Article>())
    val isLoading = MutableStateFlow(false)


    val db by lazy {
        Room.databaseBuilder(context, MyAppDataBase::class.java,"my-app-database").build()
    }

    init {
        fetchDataFromApi()
    }
    fun fetchDataFromApi(){
        isLoading.value = true
        val call = apiService.getData()
        call.enqueue(object : Callback<NewsDataModel> {
            override fun onResponse(call: Call<NewsDataModel>, response: Response<NewsDataModel>) {
                if (response.isSuccessful) {
                    _dataApi.value = response.body()!!.articles
                    isLoading.value=false
                }
            }

            override fun onFailure(call: Call<NewsDataModel>, t: Throwable) {

            }
        })
    }

    @Composable
    fun fetchDataFromDb(): List<Article> {
        _dataDb.value= db.newsDao().getAllNews().collectAsState(initial = emptyList()).value
        return _dataDb.value
    }

    fun insertDataToDb(article: Article){
        viewModelScope.launch {
            db.newsDao().insertNews(article)
        }
    }

}