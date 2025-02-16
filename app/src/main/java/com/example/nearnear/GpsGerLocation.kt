package com.example.nearnear

import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat

/*
 * 位置情報を取得するメソッド
 */
fun getLocation(context: Context): Location? {
    //パーミッション（権限）の確認　
    ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
}