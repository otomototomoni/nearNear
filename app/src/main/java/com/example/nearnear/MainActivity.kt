package com.example.nearnear

import android.content.Context
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.example.nearnear.Gps.checkLocationPermission
import com.example.nearnear.ui.theme.NearNearTheme

/*
 * AppNavHost.ktのAppNavHost()を実行して画面遷移の処理をする
 * GPSのパーミッションの権限をチェックする。権限が無効の場合、ユーザーに権限を要求する
 */
class MainActivity : ComponentActivity() {

    //取得する権限の配列を準備する
    private val REQUIRED_PERMISSIONS =
        mutableListOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ).toTypedArray()
    //リクエストコード
    private val REQUEST_CODE_PERMISSIONS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearNearTheme {
            }

            //GPSのパーミッションの権限チェック
                if (checkLocationPermission(this)) {
                    //すでに権限がある場合の処理
                    AppNavHost()
                }else {
                    //権限がない場合の処理（ユーザーに権限を要求）& ToDo:パーミッションが必要な理由を説明するUIを表示
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){
                        //ユーザーに権限を要求する
                        ActivityCompat.requestPermissions(
                            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                        )
                    }else {
                        //パーミッションをリクエストする
                        ActivityCompat.requestPermissions(
                            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                        )
                    }
                }
        }
    }

    //確認ダイアログの結果を受け取る
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //再度権限をチェックする
        if (checkLocationPermission(this)) {
            //確認ダイアログで権限を取得した場合の処理
        } else {
            //権限を取得できなかった場合の処理
            finish()
        }
    }

}