package com.example.nearnear.Gps

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/*
* パーミッションの確認メソッド
*
* GPSを使用していいのか確認し、OFFになっていたらユーザーにGPSの使用を許可してもらう
* 返り値：許可されているならtrue、許可されていないならfalse
* ToDo:メソッド名を変更したい。if分に（！変数）と入れたときにわかりやすいように
*/
fun checkLocationPermission(context: Context): Boolean {
    //パーミッション（権限）の確認　。これがないとgetLastKnownLocationがエラーになる
    //PackageManager.PERMISSION_GRANTED：パーミッションが許可されているかどうかを判断するための基準値
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}