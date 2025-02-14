package com.example.nearnear

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

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