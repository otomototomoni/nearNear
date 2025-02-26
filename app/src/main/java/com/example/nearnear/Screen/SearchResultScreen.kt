package com.example.nearnear.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nearnear.HotPepperApi.ResponseData
import com.example.nearnear.HotPepperApi.Shop
import com.example.nearnear.MainViewModel
import com.example.nearnear.R
import com.google.gson.Gson
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

    //ページングするための変数
    var startIndex by remember { mutableStateOf(1) }
    var endIndex by remember { mutableStateOf(5) }
    //shopListが初期化されるまで6の値を持ち、shopListが初期化されるとその値がこの中に入る。
    var shopListSize by remember { mutableStateOf(6) }

    Scaffold(
        //ホットペッパーAPIのクレジットを表示
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("【画像提供：ホットペッパー グルメ】")
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.hotpepper_s),
                        contentDescription = null,
                        contentScale = FillWidth,
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            //背景
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //上部のボタンを表示
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //前のリストを表示
                    Button(
                        onClick = {
                            startIndex -= 5
                            endIndex -= 5
                            if(startIndex < 1){
                                startIndex = 1
                                endIndex = 5
                            }
                        },
                        enabled = startIndex > 1
                    ) {
                        Text("前の商品を表示")
                    }
                    //検索画面へ戻るボタンを
                    Button(
                        onClick = {
                            navController.navigate("searchCondition")
                        }
                    ) {
                        Text("前の画面に戻る")
                    }

                    //次のリストを表示
                    Button(
                        onClick = {
                            startIndex += 5
                            endIndex += 5
                            if(startIndex > shopListSize){
                                endIndex = shopListSize
                            }
                        },
                        enabled = endIndex < shopListSize
                    ) {
                        Text("次の商品を表示")
                    }
                }

                //飲食店情報尾のリストを表示するところ
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .clip(
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color.Blue,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .verticalScroll(rememberScrollState())
                        .background(color = Color.White)
                    //スクロール可能にする
                ) {
                    if (responseData != null) {
                        //データをスライスする
                        val shopList = responseData!!.results.shop
                        shopListSize = shopList.size
                        val slicedShopList = shopList.subList(
                            startIndex,
                            endIndex.coerceAtMost(shopList.size)
                        )
                        slicedShopList.forEach { shop ->
                            ShopList(navController, shop)
                        }
                    } else {
                        Text(
                            text = "店情報取得中...しばらくお待ちください。",
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }
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
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                color = Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                //data classをJsonに変換
                val shopJson = Gson().toJson(shop)
                //Jsonをエンコード
                val encodedShop = URLEncoder.encode(shopJson, StandardCharsets.UTF_8.toString())
                navController.navigate(
                    "storeDetail/${encodedShop}"
                )
            }
            .background(color = Color.White)
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