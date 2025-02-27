package com.example.nearnear.Screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nearnear.HotPepperApi.Shop
import com.example.nearnear.MainViewModel
import com.example.nearnear.Screen.ScreenUtils.ScreenUtils
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * 店舗詳細画面
 * --機能--
 * ToDo
 *  検索結果画⾯で選択した店舗の情報を表⽰
 *  ・店舗名称
 *  ・住所
 *  ・営業時間
 *  ・画像
 *  詳細画⾯にあるべきと思われる機能を実装してください。
 *
 * @param navController 画面遷移を行うためのコンテナ
 * @param shop 検索結果画面から渡された店舗情報 データクラスに戻す必要がある。
 */
@Composable
fun StoreDetailScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    shop: String?
    ){
    //エンコードされたshopデータをデコードし、データクラスに戻している。
    // URL デコード
    val decodedJson = URLDecoder.decode(shop, StandardCharsets.UTF_8.toString())
    // JSON をオブジェクトに戻す
    val shop = Gson().fromJson(decodedJson, Shop::class.java)

    //背景やボトムバーなどのUIを持っているクラスのインスタンスを作成。
    val ScreenUtils = ScreenUtils()

    Scaffold(
        bottomBar = { ScreenUtils.BottomBar() }
    ) { innerPadding ->
        //背景
        ScreenUtils.BackGround(innerPadding)

        //店情報
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ){
            Text(shop!!.name)
            AsyncImage(
                model = shop.photo.mobile.l,
                contentDescription = null
            )
            Text(shop.address)
            Text(shop.open)

            //testButton　ToDo：消す
            Button(
                onClick = { navController.navigate("searchCondition") }
            ) {
                Text("Topへ")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun StoreDetailScreenPreview(){
//    val navController = rememberNavController()
//    val viewModel : MainViewModel = viewModel()
//    StoreDetailScreen(
//        navController,
//        viewModel,
//    )
//}