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

    //GPSプロバイダが有効になっているか確認するメソッド
    fun isGPSEnabled(): Boolean{
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //ネットワークプロバイダが有効になっているか確認
    fun isNetworkEnabled(): Boolean{
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    //GPSの位置情報を取得するリクエスト
    fun requestGPSLocation(locationListener: LocationListener){
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
            Log.d("LocationUtils.kt requestGPSLocation()","requestLocationUpdatesが実行されました。")//ToDO:後で消す
        } catch (e: SecurityException){
            Log.e("LocationUtils.kt requestGPSLocation()","SecurityException: ${e.message}")
        }
    }

    //ネットワークでの位置情報を取得するリクエスト
    fun requestNetworkLocation(locationListener: LocationListener){
        try {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0f,
                locationListener
            )
            Log.d("LocationUtils.kt requestNetworkLocation()","requestLocationUpdatesが実行されました。")//ToDO:後で消す
        } catch (e: SecurityException){
            Log.e("LocationUtils.kt requestNetworkLocation()","SecurityException: ${e.message}")
        }
    }

    //ネットワークプロバイダを使用して最後に取得した位置情報を取得する
    fun getLastKnownLocationNetwork(context: Context): Location? {
        PermissionUtils.checkNetworkPermission(context)
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }

    //GPSプロバイダを使用して最後に取得した位置情報を取得
    fun getLastKnownLocationGPS(context: Context): Location? {
        PermissionUtils.checkGpsPermission(context)
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    //最後に取得した位置情報を取得する一連の流れメソッド
    fun getLocation(context: Context): Location? {
        //パーミッション（権限）の確認　。許可されているなら実行
        if(PermissionUtils.checkGpsPermission(context)) {
            //GPSが有効になっているかの確認。無効ならアラートを表示。有効なら現在地を取得
            if((!isGPSEnabled() == true) && (!isNetworkEnabled() == true)){
                AlertDialogUtils.promptUserToEnableGPS(context)
                return null
            }else {
                //Todo: あとでLogを消す
                Log.d("getLocation","isNetworkEnabled:${isNetworkEnabled()}")
                Log.d("getLocation","NetworkPermission:${PermissionUtils.checkNetworkPermission(context)}")
                //GPSプロバイダで実行
                requestGPSLocation(MyLocationListener)
                //Networkプロバイダで実行
                requestNetworkLocation(MyLocationListener)
                //最後に取得したGPS情報を受け取る。今までに位置情報を取得していないならnullもしくはエラーを吐く
                //GPSで位置情報を受け取れた場合にはGPS、そうでなければネットワークで位置情報を受け取る
                if(getLastKnownLocationGPS(context) != null){
                    return getLastKnownLocationGPS(context)
                }else{
                    return getLastKnownLocationNetwork(context)
                }
            }
        }else{
            Log.d("LocationUtils.kt getLocation()","GPSのパーミッションが許可されていません。")
            return null
        }
    }
}

