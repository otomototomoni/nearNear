package com.example.nearnear.Gps

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority

/*
 * Fused Location Provider APIで現在地を取得
 * インスタンスを作成して、LocationUtils.init(context)と定義することで初期化ができる
 */
class LocationUtils{
    //現在地の取得するFused Location Client
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var context : Context
    //LocationRequestの定義：GPSとネットワークプロバイダを使用した高精度の位置情報取得。(1秒間隔)
    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
    //LocationCallBack：位置情報を取得した際に実行したい処理
    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult( locationResult: LocationResult){
            for(location in locationResult.locations){
                Log.d("位置情報",location.toString())
                Log.d("位置情報：緯度", location.latitude.toString())
                Log.d("位置情報：軽度", location.longitude.toString())
            }
        }
    }

    //Fused Location Clientの初期化
    fun init(activityContext: Context){
        context = activityContext
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    //最後に取得された位置情報を取得する
    fun getLastLocation():Location?{
        var getLastLocation : Location? = null
        //パーミッションが許可されているか確認
        if(PermissionUtils.checkGpsPermission(context) != true && PermissionUtils.checkNetworkPermission(context) != true){
            Log.d("getLastLocation","パーミッションが許可されていません。")
        }else{
            //位置情報が取得できたかどうかをLogで出力
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    location?.let{
                        getLastLocation = location
                        Log.d("getLastLocation","緯度は${location.latitude}、経度は${location.longitude}です")
                    }?:run{
                        Log.d("getLastLocation","位置情報がnullです。")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("getLastLocation","位置情報の取得に失敗しました。エラー：${e.message}")
                }
        }
        return getLastLocation
    }

    //新たに位置情報の更新を依頼する
    fun requestLocationUpdates(){
        //パーミッションチェックを行わないとrequestLocationUpdatesを実行できない
        if(PermissionUtils.checkGpsPermission(context)) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallBack,
                //null：UIの更新を直接行えない。Looper.getMainLooper()：UIの更新をハンドラーに任せる
                null
            )
        }
    }

    //観測を停止
    fun removeLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

    //Screenで実行する処理：位置情報が取得できたらそのまま返す。そうでなければリクエストをする
    fun getLocation():Location?{
        if(getLastLocation() != null){
//            removeLocationUpdates()
            return getLastLocation()
        }else{
            requestLocationUpdates()
            return null
        }
    }
}

