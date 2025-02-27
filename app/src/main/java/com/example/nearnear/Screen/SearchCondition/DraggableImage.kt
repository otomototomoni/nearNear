package com.example.nearnear.Screen.SearchCondition

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.nearnear.R
import kotlin.math.roundToInt

/*
 * 猫のイラストを横に移動させれるUI
 * イラストが右にスライドされればされるほど検索範囲が広くなる
 * SearchRangeText.ktでイラストのx座標に応じて検索範囲を表示
 */
@Composable
fun draggableImage():Int {

    // 猫のX座標を管理する状態変数
    var catX by remember { mutableStateOf(0f) }
    //横長のバーの横のサイズ
    var horizonBarWidth by remember { mutableStateOf(0) }
    //Imageの横のサイズ
    var catImageWidth by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 48.dp,
                end = 48.dp
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        //横に長い線の画像
        Image(
            painter = painterResource(id = R.drawable.current_location_horizonbar),
            contentDescription = null,
            contentScale = Crop,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    horizonBarWidth = coordinates.size.width
                }
        )
        //走っている猫の画像。横にスライドできるようになっている。
        Image(
            painter = painterResource(id = R.drawable.cat_running),
            contentDescription = null,
            contentScale = Crop,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    catImageWidth = coordinates.size.width
                }
                //catX.roundToInt() - (catImageWidth / 2)この計算をすることでバーの左端と猫の中心点を合わせれる
                .offset { IntOffset(catX.roundToInt() - (catImageWidth / 2), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        catX += dragAmount
                    }
                }
        )
    }
    //イラストのx座標が0より下になろうとしたら0で止める
    if(catX < 0f) catX = 0f
    //イラストのx座標がバーの長さを超えようとしたらバーの長さで止める
    if(catX > horizonBarWidth) catX = horizonBarWidth.toFloat()
    //ホットペッパーAPIでrangeを指定するときの1,2,3,4,5を検索範囲に応じて返す。
    return searchRangeText(catX,horizonBarWidth)
}