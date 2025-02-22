package com.example.nearnear

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nearnear.HotPepperApi.Shop
import com.example.nearnear.Screen.LocationPermissionScreen
import com.example.nearnear.Screen.SearchConditionScreen
import com.example.nearnear.Screen.SearchResultScreen
import com.example.nearnear.Screen.StoreDetailScreen
import com.example.nearnear.Screen.TitleScreen

/**
 * アプリケーションの画面遷移に関するコンポーザブル
 *
 * @param navController 画面遷移を行うためのコンテナ
 * @param startDestination 最初に表示する画面のID
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "title"
){
    val viewModel : MainViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable("title"){ TitleScreen(navController = navController) }
        composable("searchCondition"){ SearchConditionScreen(navController = navController,viewModel = viewModel) }
        composable("searchResult"){ SearchResultScreen(navController = navController,viewModel = viewModel) }
        composable(
            route = "storeDetail/{shopName}/{shopAddress}/{shopPhoto}/{shopOpen}",
            arguments = listOf(
                navArgument("shopName") { type = NavType.StringType},
                navArgument("shopAddress") { type = NavType.StringType},
                navArgument("shopPhoto") { type = NavType.StringType},
                navArgument("shopOpen") { type = NavType.StringType}
            )
        ){ navBackStackEntry ->
            val shopName = navBackStackEntry.arguments?.getString("shopName")
            val shopAddress = navBackStackEntry.arguments?.getString("shopAddress")
            val shopPhoto = navBackStackEntry.arguments?.getString("shopPhoto")
            val shopOpen = navBackStackEntry.arguments?.getString("shopOpen")
            StoreDetailScreen(
                navController = navController,
                viewModel = viewModel,
                shopName = shopName?: "",
                shopAddress = shopAddress?: "",
                shopPhoto = shopPhoto?: "",
                shopOpen = shopOpen?: ""
            )
        }
        composable("locationPermission") { LocationPermissionScreen(navController = navController,viewModel = viewModel) }
    }
}