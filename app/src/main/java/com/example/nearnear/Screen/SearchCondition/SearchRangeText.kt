package com.example.nearnear.Screen.SearchCondition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
 * 猫を移動させた距離に応じて検索範囲を表示
 * ホットペッパーAPIで検索範囲を指定するのに使うrange(1,2,3,4,5)のどれかを検索範囲に応じて返す
 */
@Composable
fun SearchRangeText(range: Float,imageWidth: Int): Int{
    //表示する検索範囲
    var searchRange by remember { mutableStateOf(0) }
    //ホットペッパーAPIを実行するときのrange(1,2,3,4,5)の値
    var ApiRange by remember { mutableStateOf(1) }

    if(imageWidth*0.25 > range){
        searchRange = 300
        ApiRange = 1
    }else if(imageWidth*0.5 > range) {
        searchRange = 500
        ApiRange = 2
    }else if(imageWidth*0.75 > range) {
        searchRange = 1000
        ApiRange = 3
    }else if(imageWidth > range) {
        searchRange = 2000
        ApiRange = 4
    }else {
        searchRange = 3000
        ApiRange = 5
    }

    //検索範囲のTextを表示
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = "検索範囲：${searchRange}m",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal,
            color = Color.Black,
        )
    }

    return ApiRange
}
