package com.example.nearnear.Screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
    shop: String?
    ){
    //エンコードされたshopデータをデコードし、データクラスに戻している。
    // URL デコード
    val decodedJson = URLDecoder.decode(shop, StandardCharsets.UTF_8.toString())
    // JSON をオブジェクトに戻す
    val shop = Gson().fromJson(decodedJson, Shop::class.java)

    var screenHeight by remember { mutableStateOf(100) }

    //背景やボトムバーなどのUIを持っているクラスのインスタンスを作成。
    val ScreenUtils = ScreenUtils()

    Scaffold(
        //ホットペッパーAPIクレジット
        bottomBar = { ScreenUtils.BottomBar() },
        containerColor = Color.LightGray
    ) { innerPadding ->
        //背景
        ScreenUtils.BackGround(innerPadding)

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    navController.navigate("searchResult")
                }
            ) {
                Text("＜戻る")
            }
            //店情報
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .onGloballyPositioned { coorDinates ->
                        screenHeight = coorDinates.size.height
                    }
                    .verticalScroll(rememberScrollState())
            ) {
                //店名
                Text(
                    text = shop!!.name,
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height((screenHeight * 0.2).dp)
                ) {
                    //店の画像
                    AsyncImage(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxSize(),
                        model = shop.photo.mobile.l,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                //営業時間
                Text(
                    text = "営業時間",
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = shop.open,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                //アドレス
                Text(
                    text = "ADERESS",
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = shop.address,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
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