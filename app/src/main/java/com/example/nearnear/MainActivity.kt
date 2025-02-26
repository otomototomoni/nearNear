package com.example.nearnear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nearnear.ui.theme.NearNearTheme

/*
 * AppNavHost.ktのAppNavHost()を実行して画面遷移の処理をする
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearNearTheme {
               AppNavHost(this)
            }
        }
    }
}