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
    //ToDo: Scaffoldを使用した画面の構築

    //test　ToDo: 消す
    Button(
        onClick = { navController.navigate("searchResult") }
    ) {
        Text("検索")
    }
}

@Preview(showBackground = true)
@Composable
fun SearchConditionScreenPreview(){
    val navController = rememberNavController()

    //画面を全体表示させるためのもの　ToDo:後で消して画面のメソッドのみにする
    Scaffold(
        topBar = {Text("検索結果画面")},
        bottomBar = {Text("ボトムバー")}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            SearchConditionScreen(navController = navController)
        }
    }
}