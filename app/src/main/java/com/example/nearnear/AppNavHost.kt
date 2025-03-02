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
import com.example.nearnear.Screen.LocationPermissionScreen
import com.example.nearnear.Screen.SearchCondition.SearchConditionScreen
import com.example.nearnear.Screen.SearchResult.SearchResultScreen
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
        composable("title"){ TitleScreen(navController = navController) }
        composable("searchCondition"){ SearchConditionScreen(context = context,navController = navController,viewModel = viewModel) }
        //表示するページ数を引数として渡さないと、画面遷移した際に１ページ目からになる
        composable("searchResult"){ SearchResultScreen(navController = navController, viewModel = viewModel) }
        //DataClassを受け渡ししたいため、ParcelableTypeを使用している
        //streDetaileを指定するときに("storeDetail/(DataClass)")とすることで指定したDataClassを渡せる。
        composable(
            "storeDetail/{shop}",
            arguments = listOf(
                navArgument("shop") { type = NavType.StringType}
            )
        ){ navBackStackEntry ->
            //エンコードされたものを受け取る
            val shop = navBackStackEntry.arguments?.getString("shop")
            StoreDetailScreen(
                navController = navController,
                shop = shop
            )
        }
        composable("locationPermission") { LocationPermissionScreen(navController = navController,viewModel = viewModel) }
    }
}