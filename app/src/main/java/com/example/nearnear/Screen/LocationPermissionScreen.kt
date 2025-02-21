package com.example.nearnear.Screen

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.nearnear.AppNavHost
import com.example.nearnear.Gps.PermissionUtils
import com.example.nearnear.MainActivity
import com.example.nearnear.MainViewModel

@Composable
fun LocationPermissionScreen(navController: NavHostController, viewModel: MainViewModel) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    var showPermissionRationale by remember { mutableStateOf(false) }
    var isPermanentlyDenied by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
        if (!isGranted) {
            // パーミッションが拒否された場合
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // ユーザーが「今後表示しない」を選択していない場合
                showPermissionRationale = true
            } else {
                // ユーザーが「今後表示しない」を選択した場合
                isPermanentlyDenied = true
            }
        }
        Log.d("Launcher","permissionGranted:${permissionGranted}")
        Log.d("Launcher","showPermissionRationals:${showPermissionRationale}")
        Log.d("Launcher","isPermanentlyDenied:${isPermanentlyDenied}")
    }

    LaunchedEffect(key1 = true) {
        // アプリ起動時にパーミッションをチェック
        if (PermissionUtils.checkGpsPermission(context)) {
            Log.d("LaunchedEffect","checkGpsPermissionがtrueです。")
            permissionGranted = true
        } else {
            Log.d("LaunchedEffect","checkGpsPermissionがfalseです。")
            // パーミッションが許可されていない場合、リクエストを開始
            locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (permissionGranted) {
            Text("GPSパーミッションは許可されています。")
            // パーミッションが許可された場合の処理
            Log.d("MainActivity.kt", "GPSパーミッションが許可されています。")
            //すでに権限がある場合の処理
            navController.navigate("searchCondition")
        } else if (showPermissionRationale) {
            Text("このアプリケーションを使用するにはGPSパーミッションが必要です。")
            Button(onClick = {
                // パーミッションを再度リクエスト
                locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                showPermissionRationale = false
            }) {
                Text("もう一度パーミッションを表示する。")
            }
        } else if (isPermanentlyDenied) {
            Text("GPSパーミッションが拒否されました。アプリケーションを使用するには下のボタンから設定に行き、「Permission」の「Location」を許可してください。")
            Button(onClick = {
                // アプリの設定画面を開く
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }) {
                Text("Open App Settings")
            }
        } else {
            Text("Location permission is required to use this app.")
        }
    }
}