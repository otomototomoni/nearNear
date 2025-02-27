package com.example.nearnear.Screen.SearchResult

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nearnear.R

@Composable
fun topButtons(startIndex: Int, endIndex: Int, shopListSize: Int, navController: NavHostController, screenWidth: Int): Pair<Int,Int>{
    var startIndex by remember { mutableStateOf(startIndex) }
    var endIndex by remember { mutableStateOf(endIndex) }
    var shopListPage by remember { mutableStateOf((Math.ceil(shopListSize/5.0).toInt())) }
    var currentPage by remember { mutableStateOf(1) }
    //画像の大きさ ページ数の表示もこの変数を使用
    val imageSize = screenWidth*0.05

    //LaunchedEffectでshopListSizeの変更を見ることで正しく全体のページ数を表示、更新できる
    LaunchedEffect(shopListSize) {
        shopListPage = Math.ceil(shopListSize/5.0).toInt()
    }

    //上部のボタンを表示
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //検索画面へ戻るボタンを
        Button(
            onClick = {
                navController.navigate("searchCondition")
            }
        ) {
            Text("＜検索画面へ")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            //前のリストを表示 startIndexが1より小さい時にクリックできないようにし、色を薄くする
            if (startIndex > 1) {
                Image(
                    painter = painterResource(id = R.drawable.go_previous_page),
                    contentDescription = null,
                    contentScale = Crop,
                    modifier = Modifier
                        .clickable {
                            startIndex -= 5
                            endIndex -= 5
                            currentPage -= 1
                        }
                        .size(imageSize.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.cannot_go_previous_page),
                    contentDescription = null,
                    contentScale = Crop,
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .size(imageSize.dp)
                )
            }

            //現在のページと総合ページ数を表示
            Text(
                text = "${currentPage}/${shopListPage}",
                modifier = Modifier
                    .padding(start = 8.dp),
                //imageSizeに合わせる
                fontSize = imageSize.sp,
                fontWeight = FontWeight.ExtraBold
            )

            //次のリストを表示 endIndexがshopListSizeより大きい時にクリックできないようにし、色を薄くする
            if (endIndex < shopListSize) {
                Image(
                    painter = painterResource(id = R.drawable.go_next_page),
                    contentDescription = null,
                    contentScale = Crop,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            startIndex += 5
                            endIndex += 5
                            currentPage += 1
                        }
                        .size(imageSize.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.cannot_go_next_page),
                    contentDescription = null,
                    contentScale = Crop,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(imageSize.dp)
                )
            }
        }
    }
    return Pair(startIndex,endIndex)
}