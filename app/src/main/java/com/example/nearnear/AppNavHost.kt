package com.example.nearnear

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nearnear.Screen.SearchCondition.searchConditionScreen
import com.example.nearnear.Screen.SearchResult.searchResultScreen
import com.example.nearnear.Screen.locationPermissionScreen
import com.example.nearnear.Screen.storeDetailScreen
import com.example.nearnear.Screen.titleScreen

/**
 * アプリケーションの画面遷移に関するコンポーザブル
 *
 * @param navController 画面遷移を行うためのコンテナ
 * @param startDestination 最初に表示する画面のID
 */
@Composable
fun AppNavHost(
    context: Context,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "title"
){
    val context : Context = context
    val viewModel : MainViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable("title"){ titleScreen(navController = navController) }
        composable("searchCondition"){ searchConditionScreen(context = context,navController = navController,viewModel = viewModel) }
        composable("searchResult"){ searchResultScreen(navController = navController,viewModel = viewModel) }
        //DataClassを受け渡ししたいため、ParcelableTypeを使用している
        //streDetaileを指定するときに("storeDetail/(DataClass)")とすることで指定したDataClassを渡せる。
        composable(
            "storeDetail/{shop}",
            arguments = listOf(
                navArgument("shop") { type = NavType.StringType},
            )
        ){ navBackStackEntry ->
            //エンコードされたものを受け取る
            val shop = navBackStackEntry.arguments?.getString("shop")
            storeDetailScreen(
                navController = navController,
                viewModel = viewModel,
                shop = shop
            )
        }
        composable("locationPermission") { locationPermissionScreen(navController = navController,viewModel = viewModel) }
    }
}