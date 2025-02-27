package com.example.nearnear.Screen.SearchResult

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
fun ShopList(navController: NavHostController, shop: Shop){
    Box(
        modifier = Modifier
            .fillMaxWidth()
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