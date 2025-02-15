package com.example.nearnear

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * アプリケーションの画面遷移に関するコンポーザブル
 *
 * @param navController 画面遷移を行うためのコンテナ
 * @param startDestination 最初に表示する画面のID
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "searchCondition"
){
    val viewModel : MainViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        //
        composable("searchCondition"){ SearchConditionScreen(navController = navController,viewModel = viewModel) }
        composable("searchResult"){ SearchResultScreen(navController = navController,viewModel = viewModel()) }
        composable("storeDetail"){ StoreDetailScreen(navController = navController,viewModel = viewModel())}
    }
}