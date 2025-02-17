package com.example.nearnear.Gps

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings

fun promptUserToEnableGPS(context: Context) {
    if (!LocationUtils.isGPSEnabled(context)) {
        AlertDialog.Builder(context)
            .setMessage("GPSがオフになっています。位置情報を正確に取得するためには、GPSをオンにしてください。")
            .setPositiveButton("設定") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }
            .setNegativeButton("キャンセル", null)
            .create()
            .show()
    }
}