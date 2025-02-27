package com.example.nearnear.Screen.ScreenUtils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.res.painterResource
import com.example.nearnear.R

/*
 * 様々なScreenで共有することができるもの
 */
class ScreenUtils{

    //ホットペッパーAPIのクレジットをBottomバーとして表示
    @Composable
    fun bottomBar() {
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

    @Composable
    fun backGround(){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }

}