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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nearnear.Gps.AlertDialogUtils
import com.example.nearnear.Gps.LocationUtils
import com.example.nearnear.Gps.PermissionUtils
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
fun SearchConditionScreen(navController: NavHostController,viewModel: MainViewModel){
    //LocationUtilsクラスのインスタンス作成
    val LocationUtils = LocationUtils()
    val context : Context = LocalContext.current
    LocationUtils.init(context)

    var imageValue by remember { mutableStateOf(0f) }
    var range by remember { mutableStateOf(1) }
    var searchRadius by remember { mutableStateOf(300) }

    //Todo:test
    val responseLocation by viewModel.responseLocation.collectAsState()

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
            imageValue = DraggableImage()
            range = Range(imageValue)
            searchRadius = SerchRadius(range)

            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "検索半径：${searchRadius}m"
            )

            //GPSかネットワークで位置情報を取得し、ホットペッパーAPIから店情報を取得。次の画面へ遷移
            //パーミッションが許可されていない場合はパーミッションを許可を求める画面へ遷移
            Button(
                onClick = {
                    println("KotlinVersion -> ${KotlinVersion.CURRENT}")
                    if(!PermissionUtils.checkGpsPermission(context)) {
                        Log.d("SearchConditionScreen.kt", "GPSパーミッションがオフです。")
                        navController.navigate("locationPermission")
                    }else if(!LocationUtils.isGPSEnabled()) {
                        Log.d("SearchConditionScreen.kt", "GPSがオフです。")
                        AlertDialogUtils.promptUserToEnableGPS(context)
                    }else {
                        //GPSから現在地の取得。LocationUtilsオブジェクトのメソッドを実行
                        val location = LocationUtils.getLocation(context)
                        Log.d(
                            "SearchConditionScreen.kt","GPSがオンです。\n緯度は${location?.latitude}、経度は${location?.longitude}です。"
                        )
                        // API リクエストを実行
                        val shops = viewModel.getShops(
                            latitude = responseLocation?.latitude ?: 34.404896, // null：尾道駅の緯度
                            longitude = responseLocation?.longitude ?: 133.193555, // null：尾道駅の経度
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

fun Range(imageValue: Float):Int{
    if(imageValue < 100){
        return 1
    }else if(imageValue < 200){
        return 2
    }else if(imageValue < 300){
        return 3
    }else if(imageValue < 400){
        return 4
    }else{
        return 5
    }
}

fun SerchRadius(range: Int):Int{
    if(range == 1){
        return 300
    }else if(range == 2){
        return 500
    }else if(range == 3){
        return 1000
    }else if(range == 4){
        return 2000
    }else{
        return 3000
    }
}

@Composable
fun DraggableImage():Float {
    // 円の中心のX座標を管理する状態変数
    var circleX by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cat_fish_run),
            contentDescription = null,
            modifier = Modifier
                .offset { IntOffset(circleX.roundToInt(), 0) }
                .size(100.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        circleX += dragAmount
                    }
                }
        )
    }
    if(circleX < 0f) circleX = 0f
    if(circleX > 500f) circleX = 500f
    return circleX
}

@Preview(showBackground = true)
@Composable
fun SearchConditionScreenPreview(){
    val navController = rememberNavController()
    SearchConditionScreen(navController,viewModel = viewModel())
}