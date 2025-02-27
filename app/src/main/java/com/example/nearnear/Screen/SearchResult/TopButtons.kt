package com.example.nearnear.Screen.SearchResult

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun TopButtons(startIndex: Int, endIndex: Int, shopListSize: Int,navController: NavHostController): Pair<Int,Int>{
    var startIndex by remember { mutableStateOf(startIndex) }
    var endIndex by remember { mutableStateOf(endIndex) }
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

        //前のリストを表示
        Button(
            onClick = {
                startIndex -= 5
                endIndex -= 5
            },
            //startIndexが0の時に前のリストを表示できないように
            enabled = startIndex > 1
        ) {
            Text("前のページ")
        }

        //次のリストを表示
        Button(
            onClick = {
                startIndex += 5
                endIndex += 5
            },
            //endIndexがshopListSizeより大きい時に次のリストを表示できないように
            enabled = endIndex < shopListSize
        ) {
            Text("次のページ")
        }
    }
    return Pair(startIndex,endIndex)
}