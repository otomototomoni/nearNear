package com.example.nearnear.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nearnear.MainViewModel

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
fun SearchResultScreen(navController: NavHostController,viewModel: MainViewModel) {
    //viewModelからresponseDataを取得
    val responseData by viewModel.responseData.collectAsState()

    Scaffold(
        topBar = {Text("検索結果画面")},
        bottomBar = {Text("ボトムバー")}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
            //スクロール可能にする
        ){
            //testButton　ToDo：消す
            Button(
                onClick = {
                    navController.navigate("storeDetail")
                }
            ) {
                Text("このお店を表示")
            }

            if(responseData != null){
                responseData!!.results.shop.forEach { shop ->
                    Text(text = "shop Name : ${shop.name}")
                    Text(text = "shop Address : ${shop.address}")
                    Text(text = "shop Access : ${shop.access}")
                }
            }else{
                Text("APIリクエスト失敗")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview(){
    val navController = rememberNavController()
    SearchResultScreen(navController,viewModel = viewModel())
}