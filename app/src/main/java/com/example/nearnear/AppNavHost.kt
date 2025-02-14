package com.example.nearnear

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/*
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
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        //
        composable("searchCondition"){ SearchConditionScreen(navController = navController) }
        composable("searchResult"){ SearchResultScreen(navController = navController) }
        composable("storeDetail"){ StoreDetailScreen(navController = navController)}
    }
}