package com.example.nearnear.Gps

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task

/*
 * Fused Location Provider APIを使用して現在地を取得する
 */
class LocationService(context: Context) {

    val context = context

    //FusedLocationProviderClientの初期化
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    //lateinitで初期化を遅延させる
    lateinit var locationCallback: LocationCallback
    lateinit var locationRequest : LocationRequest

    // 位置情報のリクエストを設定
    fun startLocationUpdates() {
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000
        ).build()

        locationCallback =
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        Log.d(
                            "LocationService",
                            "リアルタイム 緯度: ${location.latitude}, 経度: ${location.longitude}"
                        )
                    }
                }
            }

        if(PermissionUtils.checkGpsPermission(context)) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    // 位置情報の更新を停止する
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun getCurrentLocation():Location{
        var getLocation : Location = Location("")
        if(PermissionUtils.checkGpsPermission(context)) {
            val locationTask: Task<Location> = fusedLocationClient.lastLocation
            locationTask.addOnSuccessListener { location ->
                if (location != null) {
                    Log.d("MainActivity", "緯度: ${location.latitude}, 経度: ${location.longitude}")
                    getLocation = location
                } else {
                    Log.e("MainActivity", "位置情報が取得できませんでした")
                }
            }.addOnFailureListener { e ->
                Log.e("MainActivity", "位置情報の取得に失敗: ${e.localizedMessage}")
            }
        }
        return getLocation
    }
}