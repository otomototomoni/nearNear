package com.example.nearnear.Gps

import android.location.LocationListener
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.example.nearnear.MainViewModel

/*
 * LocationListenerをまとめておくところ
 */
object MyLocationListener: LocationListener{

            //位置情報が更新された際に呼び出される
            override fun onLocationChanged(location: Location) {
                val viewModel:MainViewModel = MainViewModel()
                viewModel._responseLocation.value = location
                Log.d("LocationListener","override fun onLocationChanged経度＝${location.latitude},緯度=${location.longitude}")
            }

            //プロバイダが有効になった際に呼び出される
            override fun onProviderEnabled(provider: String) {
                Log.d("LocationListener","プロバイダが有効になりました：${provider}")
            }

            //プロバイダが無効になった際に呼び出される
            override fun onProviderDisabled(provider: String) {
                Log.d("LocationListener","プロバイダが無効になりました：${provider}")
            }

            //プロバイダの状態が変更された際に呼び出される
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                Log.d("LocationListener","changed:${provider},status:${status}")
            }

}