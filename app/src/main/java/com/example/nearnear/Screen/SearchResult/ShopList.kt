package com.example.nearnear.Screen.SearchResult

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.nearnear.HotPepperApi.Shop
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/*
 * 受け取った店ごとにBoxにまとめる
 */
@Composable
fun ShopList(navController: NavHostController, shop: Shop, screenHeight: Int){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight * 0.1).dp)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
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
            //店の画像
            AsyncImage(
                model = shop.photo.mobile.l,
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        bottom = 4.dp,
                        start = 4.dp,
                        end = 8.dp,
                    )
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )

            //名前とアクセスを表示
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = shop.name,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = shop.access
                )
            }
        }
    }
}