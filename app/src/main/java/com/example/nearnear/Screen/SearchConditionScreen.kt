package com.example.nearnear.Screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nearnear.Gps.LocationUtils
import com.example.nearnear.MainViewModel

/**
 * 検索条件入力画面
 * --機能--
 * ToDo
 *  GPSを使って現在位置を取得
 *  現在地からの検索範囲を指定できるように
 *  その他機能（任意）
 *
 * @param navController 画面遷移を行うためのコンテナ
 */
@Composable
fun SearchConditionScreen(navController: NavHostController,viewModel: MainViewModel){
    //contextの取得
    val context : Context = LocalContext.current

    Scaffold(
        topBar = {Text("検索条件入力画面")},
        bottomBar = {Text("ボトムバー")}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ){
            Text(text = "検索条件を入力してください。")
            //testButton　ToDo: 消す
            Button(
                onClick = {
                    //GPSから現在地の取得。LocationUtilsオブジェクトのメソッドを実行
                    val location = LocationUtils.getLocation(context)
                    // API リクエストを実行
                    viewModel.getShops(
                        latitude = location?.latitude ?: 34.2, // 例：東京駅の緯度
                        longitude = location?.longitude ?: 139.1, // 例：東京駅の経度
                        range = 1 // 例：300m
                    )
                    navController.navigate("searchResult")
                }
            ) {
                Text("検索")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchConditionScreenPreview(){
    val navController = rememberNavController()
    SearchConditionScreen(navController,viewModel = viewModel())
}