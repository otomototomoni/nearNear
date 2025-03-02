package com.example.nearnear.Screen.SearchResult

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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nearnear.HotPepperApi.Shop
import com.example.nearnear.MainViewModel
import com.example.nearnear.R
import com.example.nearnear.Screen.ScreenUtils.ScreenUtils
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
    //背景やBottomBarをまとめてあるクラスのインスタンスを作成
    val ScreenUtils = ScreenUtils()

    //viewModelからresponseDataを取得
    val responseData by viewModel.responseData.collectAsState()

    //ページングするための変数
    //sublistのはじめのindex番号
    var startIndex by remember { mutableStateOf(0) }
    //sublistの最後のindex番号
    var endIndex by remember { mutableStateOf(5) }
    //shopListが初期化されるまで6の値を持ち、shopListが初期化されるとその値がこの中に入る。
    var shopListSize by remember { mutableStateOf(responseData?.results?.shop?.size?:0) }
    //画面全体の横幅
    var screenWidth by remember { mutableStateOf(100) }
    //店のリストを表示するColumnの画面のheightサイズを取得する
    var listScreenHeight by remember { mutableStateOf(100) }
    //現在のページ
    val currentPage by remember { mutableStateOf(endIndex/5) }

    Scaffold(
        //ホットペッパーAPIのクレジットを表示
        bottomBar = { ScreenUtils.BottomBar() },
        containerColor = Color.LightGray
    ) { innerPadding ->
        //背景
        ScreenUtils.BackGround(innerPadding)

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    screenWidth = coordinates.size.width
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //上部の画面遷移やページングを行うボタン
            val indexPair: Pair<Int, Int> =
                topButtons(startIndex, endIndex, shopListSize, navController, screenWidth)
            startIndex = indexPair.first
            endIndex = indexPair.second

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
                    .onGloballyPositioned { coordinates ->
                        listScreenHeight = coordinates.size.height
                    }
                    //スクロール可能にする
                    .verticalScroll(rememberScrollState())
                    .background(color = Color.White)
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
                        ShopList(navController, shop, listScreenHeight)
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

@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview(){
    val navController = rememberNavController()
    SearchResultScreen(navController,viewModel = viewModel())
}