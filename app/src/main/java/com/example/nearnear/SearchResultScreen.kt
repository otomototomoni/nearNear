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
 * 検索結果画面
 * --機能--
 * ToDo
 *  各店舗の項目の表示
 *  ・店舗名
 *  ・アクセス
 *  ・サムネイル画像
 *  可能であればページングに対応
 *
 * @param navController 画面遷移を行うためのコンテナ
 */
@Composable
fun SearchResultScreen(navController: NavHostController) {

    //test　ToDo：消す
    Button(
        onClick = { navController.navigate("storeDetail") }
    ) {
        Text("このお店を表示")
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview(){
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
            SearchResultScreen(navController = navController)
        }
    }
}