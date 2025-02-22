package com.example.nearnear.Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nearnear.HotPepperApi.Shop
import com.example.nearnear.MainViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
                    navController.navigate("searchCondition")
                }
            ) {
                Text("前の画面に戻る")
            }

            if(responseData != null){
                responseData!!.results.shop.forEach { shop ->
                    ShopList(navController,shop)
                }
            }else{
                Text("店情報取得中...しばらくお待ちください。")
            }
        }
    }
}

@Composable
fun ShopList(navController: NavHostController,shop: Shop){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                val encodedShopName = URLEncoder.encode(shop.name, StandardCharsets.UTF_8.toString())
                val encodedShopAddress = URLEncoder.encode(shop.address, StandardCharsets.UTF_8.toString())
                val encodedShopPhoto = URLEncoder.encode(shop.photo.mobile.l, StandardCharsets.UTF_8.toString())
                val encodedShopOpen = URLEncoder.encode(shop.open, StandardCharsets.UTF_8.toString())
                navController.navigate(
                    "storeDetail/${encodedShopName}/${encodedShopAddress}/${encodedShopPhoto}/${encodedShopOpen}"
                )
            }
    ){
        Row(
            modifier = Modifier
                .padding(6.dp)
        ){
            AsyncImage(model = shop.photo.mobile.l, contentDescription = null)
            Column(
                modifier = Modifier
                    .padding(6.dp)
            ) {
                Text(text = shop.name)
                Text(text = shop.access)
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