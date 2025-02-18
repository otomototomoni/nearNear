package com.example.nearnear.Gps

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log

/*
 * locationManagerが重複していたため、objectで定義
 */
class LocationUtils{
    //現在地の取得するための準備。位置情報サービスを管理するためのLocationManagerオブジェクトの取得
    private lateinit var locationManager: LocationManager

    //locationManagerの初期化
    fun init(context: Context){
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    //GPSが有効になっているか確認するメソッド
    fun isGPSEnabled(): Boolean{
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //ToDo: requestLocationUpdates,新しいメソッドを作成する必要がある。
    fun requestLocation(locationListener: LocationListener){
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
            Log.d("LocationUtils.kt requestLocation()","requestLocationUpdatesが実行されました。")//ToDO:後で消す
        } catch (e: SecurityException){
            Log.e("LocationUtils.kt requestLocation()","SecurityException: ${e.message}")
        }
    }

    //ToDo: このメソッドはまとめ用のメソッドのため別のファイルでの管理をした方がいい
    //最後に取得した位置情報を取得するメソッド
    fun getLocation(context: Context): Location? {
        //パーミッション（権限）の確認　。許可されているなら実行
        if(checkLocationPermission(context)) {
            //GPSが有効になっているかの確認。無効ならアラートを表示。有効なら現在地を取得
            if(!isGPSEnabled()){
                promptUserToEnableGPS(context)
                return null
            }else {
                requestLocation(MyLocationListener)
                //最後に取得したGPS情報を受け取る。今までに位置情報を取得していないならnullもしくはエラーを吐く
                return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        }else{
            Log.d("LocationUtils.kt getLocation()","GPSのパーミッションが許可されていません。")
            return null
        }
    }
}

