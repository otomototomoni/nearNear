package com.example.nearnear.Screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nearnear.Gps.AlertDialogUtils
import com.example.nearnear.Gps.LocationUtils
import com.example.nearnear.Gps.PermissionUtils
import com.example.nearnear.Gps.Provider
import com.example.nearnear.MainViewModel
import com.example.nearnear.R
import kotlin.math.roundToInt

/**
 * 検索条件入力画面
 * --機能--
 * ToDo
 *  GPSを使って現在位置を取得
 *  現在地からの検索範囲を指定できるように
 *  その他機能（任意）
 *
 * @param navController 画面遷移を行うためのコンテナ
 */
@Composable
fun SearchConditionScreen(context:Context,navController: NavHostController,viewModel: MainViewModel){
    //Activity contextを代入
    val context : Context = context

    //LocationUtilsクラスのインスタンス作成
    val LocationUtils = LocationUtils()
    LocationUtils.init(context)

    //検索範囲
    var range by remember { mutableStateOf(1) }

    Scaffold(
        //一番下に出てくるバー
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
        //背景を入れるためのBoxコンポーザブル
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

            //背景の前にあるボタンなど
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //横にスライドできるバーを設置
                range = LocationDraggableImage()

                //GPSかネットワークで位置情報を取得し、ホットペッパーAPIから店情報を取得。次の画面へ遷移
                //パーミッションが許可されていない場合はパーミッションを許可を求める画面へ遷移
                Image(
                    painter = painterResource(id = R.drawable.search_button),
                    contentDescription = null,
                    contentScale = Crop,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .clickable {
                        if (!PermissionUtils.checkGpsPermission(context)) {
                            Log.d("SearchConditionScreen.kt", "GPSパーミッションがオフです。")
                            navController.navigate("locationPermission")
                        } else if (!Provider.isGPSProviderEnabled(context)) {
                            AlertDialogUtils.promptUserToEnableGPS(context)
                        } else {
                            //GPSから現在地の取得。LocationUtilsオブジェクトのメソッドを実行
                            val location = LocationUtils.getLocation()
                            Log.d(
                                "SearchConditionScreen.kt",
                                "GPSがオンです。\n緯度は${location?.latitude}、経度は${location?.longitude}です。"
                            )
                            // API リクエストを実行
                            val shops = viewModel.getShops(
                                latitude = location?.latitude ?: 34.404896, // null：尾道駅の緯度
                                longitude = location?.longitude ?: 133.193555, // null：尾道駅の経度
                                range = range
                            )
                            navController.navigate("searchResult")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LocationDraggableImage():Int {

    // 猫のX座標を管理する状態変数
    var catX by remember { mutableStateOf(0f) }
    //横長のバーの横のサイズ
    var horizonBarWidth by remember { mutableStateOf(0) }

    //Imageの横のサイズ
    var catImageWidth by remember { mutableStateOf(0)}

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
                .offset { IntOffset(catX.roundToInt() - (catImageWidth / 2), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        catX += dragAmount
                    }
                }
        )
    }
    if(catX < 0f) catX = 0f
    if(catX > horizonBarWidth) catX = horizonBarWidth.toFloat()
    return SearchRangeText(catX,horizonBarWidth)
}

//猫を移動させた距離に応じて検索範囲を表示
@Composable
fun SearchRangeText(range: Float,imageWidth: Int): Int{
    //表示する検索範囲
    var searchRange by remember { mutableStateOf(0) }
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

@Preview(showBackground = true)
@Composable
fun SearchConditionScreenPreview(){
    val context : Context = LocalContext.current
    val navController = rememberNavController()
    SearchConditionScreen(context,navController,viewModel = viewModel())
}