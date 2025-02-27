package com.example.nearnear.Screen.SearchCondition

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.nearnear.Screen.ScreenUtils.ScreenUtils
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

    //背景など他のScreenと同じものをまとめてるクラスのインスタンス
    val ScreenUtils = ScreenUtils()

    //LocationUtilsクラスのインスタンス作成
    val LocationUtils = LocationUtils()
    LocationUtils.init(context)

    //検索範囲
    var range by remember { mutableStateOf(1) }

    Scaffold(
        //一番下に出てくるホットペッパーAPIのクレジット
        bottomBar = { ScreenUtils.BottomBar() },
        containerColor = Color.LightGray
    ) { innerPadding ->
        //背景
        ScreenUtils.BackGround(innerPadding)

        //背景の前にあるボタンなど
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //横にスライドできるバーを設置
            //検索半径（１，２，３，４，５）を返すようにしており、rangeに代入
            range = draggableImage()

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
                            //１ページ目を表示
                            navController.navigate("searchResult")
                        }
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchConditionScreenPreview(){
    val context : Context = LocalContext.current
    val navController = rememberNavController()
    SearchConditionScreen(context,navController,viewModel = viewModel())
}