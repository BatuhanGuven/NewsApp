package com.example.news.view

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.news.bottomNavItems
import com.example.news.viewModel.MyViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MyViewModel){
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val searchTerm = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "News") },

                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(160,196,157)),
                actions = {
                    Row(modifier = Modifier
                        .fillMaxHeight()

                        .width(300.dp),
                    ){
                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                            ,
                            contentAlignment = Alignment.Center
                        ){
                            TextField(
                                value = searchTerm.value,
                                onValueChange = {searchTerm.value=it},
                                label = { Text(text = "Author")},
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(2500.dp)
                                    ,
                                shape = RoundedCornerShape(20.dp)

                            )

                        }

                        IconButton(
                            onClick = { viewModel._dataApi.value= viewModel._dataApi.value.filter { it.author.contains(searchTerm.value,ignoreCase = true)} },
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(50.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription ="ara" )
                        }

                    }
                }
            )
        },
        bottomBar ={
                   NavigationBar(containerColor = Color(225,236,200) ) {
                       bottomNavItems.forEach { item->
                           val selected = item.route == backStackEntry.value?.destination?.route
                           NavigationBarItem(selected = selected , onClick = {navController.navigate(item.route)}, icon = { Icon(
                               imageVector = item.icon,
                               contentDescription = item.name
                           )}, label = {item.name})
                       }
                   }
        } ,
        content = {
            Box(modifier = Modifier.padding(paddingValues = it)){
                NavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    startDestination = "home"
                ) {
                    composable("home") { HomeScreen(viewModel,navController) }
                    composable("favorite") { FavoriteScreen(viewModel) }
                    composable("detay/{index}", arguments = listOf (navArgument("index"){type = NavType.IntType})){ Detay(viewModel,it.arguments!!.getInt("index"))}
                }
            }
        }
    )
}

@Composable
fun HomeScreen(viewModel: MyViewModel, navController: NavController) {

    val data = viewModel._dataApi
    val bundle = Bundle()
    val isRefreshing = viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing.value)


    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.fetchDataFromApi() }) {
        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .background(Color(196, 215, 178))){
                itemsIndexed(data.value){index, item ->
                    Card(modifier = Modifier
                        .padding(2.dp)

                        .clickable {
                            bundle.apply {
                                putInt("index0", index)
                            }
                            navController.navigate("detay/$index")
                        },
                        colors = CardDefaults.cardColors(containerColor = Color(225,236,178)))   {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                        ) {
                            Box(modifier = Modifier
                                .height(120.dp)
                                .width(150.dp)){
                                AsyncImage(model = item.urlToImage, contentDescription ="haber resmi" )
                            }
                            Column{
                                Text(text = item.author, fontWeight = FontWeight.Bold)
                                Text(text = item.title)


                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FavoriteScreen(viewModel: MyViewModel){
    val list = viewModel.fetchDataFromDb()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(196, 215, 178))){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .background(Color(196, 215, 178))
        ) {
            itemsIndexed(list) {index, item ->
                Card(modifier = Modifier.padding(5.dp)) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                            contentAlignment = Alignment.Center){
                            AsyncImage(model = item.urlToImage, contentDescription ="haber resmi" )
                        }
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp)){
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(text = item.title, modifier = Modifier.padding(4.dp), fontSize = 17.sp)
                                Text(text = item.description, modifier = Modifier.padding(4.dp))
                                Text(text = item.author, modifier = Modifier.padding(4.dp))
                                Text(text = item.publishedAt, modifier = Modifier.padding(4.dp))

                            }
                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detay (viewModel: MyViewModel, index : Int){
    val selectedNews = viewModel._dataApi.value[index]
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            IconButton(onClick = {viewModel.insertDataToDb(selectedNews)
            Toast.makeText(context,"Favorilere eklendi",Toast.LENGTH_SHORT).show()} ){
                    Icon(imageVector = Icons.Default.Favorite, contentDescription ="add")
            }
        }

    ) {
        Box(modifier = Modifier
            .padding(paddingValues = it)
            .background(Color(196, 215, 178))){
            Column(modifier = Modifier.fillMaxSize()) {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                    contentAlignment = Alignment.Center){
                    AsyncImage(model = selectedNews.urlToImage, contentDescription ="haber resmi" )
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp)){
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = selectedNews.title, modifier = Modifier.padding(4.dp), fontSize = 17.sp)
                        Text(text = selectedNews.description, modifier = Modifier.padding(4.dp))
                        Text(text = selectedNews.author, modifier = Modifier.padding(4.dp))
                        Text(text = selectedNews.publishedAt, modifier = Modifier.padding(4.dp))

                    }
                }
            }
        }
    }
}




