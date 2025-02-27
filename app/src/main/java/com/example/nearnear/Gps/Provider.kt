package com.example.nearnear.Gps

import android.content.Context
import android.location.LocationManager

object Provider {
    fun isGPSProviderEnabled(context: Context) : Boolean{
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}