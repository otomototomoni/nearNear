package com.example.nearnear.Screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
        topBar = {Text("検索条件入力画面")},
        bottomBar = {Text("ボトムバー")}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ){
            Text(text = "検索条件を入力してください。")

            //横にスライドできるバーを設置
            range = LocationDraggableImage()

            //GPSかネットワークで位置情報を取得し、ホットペッパーAPIから店情報を取得。次の画面へ遷移
            //パーミッションが許可されていない場合はパーミッションを許可を求める画面へ遷移
            Button(
                onClick = {
                    if(!PermissionUtils.checkGpsPermission(context)) {
                        Log.d("SearchConditionScreen.kt", "GPSパーミッションがオフです。")
                        navController.navigate("locationPermission")
                    }else if (!Provider.isGPSProviderEnabled(context)){
                        AlertDialogUtils.promptUserToEnableGPS(context)
                    }else {
                        //GPSから現在地の取得。LocationUtilsオブジェクトのメソッドを実行
                        val location = LocationUtils.getLocation()
                        Log.d(
                            "SearchConditionScreen.kt","GPSがオンです。\n緯度は${location?.latitude}、経度は${location?.longitude}です。"
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
            ) {
                Text("検索")
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
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        //横に長い線の画像
        Image(
            painter = painterResource(id = R.drawable.current_location_horizonbar),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
                .onGloballyPositioned {coordinates ->
                    horizonBarWidth = coordinates.size.width
                }
        )
        //走っている猫の画像。横にスライドできるようになっている。
        Image(
            painter = painterResource(id = R.drawable.cat_fish_run),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .onGloballyPositioned {coordinates ->
                    catImageWidth = coordinates.size.width
                }
                .offset { IntOffset(catX.roundToInt()-(catImageWidth/2), 0) }
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

    Text(
        modifier = Modifier
            .padding(16.dp),
        text = "検索範囲：${searchRange}m"
    )

    return ApiRange
}

//@Preview(showBackground = true)
//@Composable
//fun SearchConditionScreenPreview(){
//    val navController = rememberNavController()
//    SearchConditionScreen(navController,viewModel = viewModel())
//}