package com.example.nearnear

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

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
fun SearchConditionScreen(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()
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
                    // API リクエストを実行
                    viewModel.getShops(
                        latitude = 35.681236, // 例：東京駅の緯度
                        longitude = 139.767125, // 例：東京駅の経度
                        range = 5 // 例：300m
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
    SearchConditionScreen(navController = navController)
}