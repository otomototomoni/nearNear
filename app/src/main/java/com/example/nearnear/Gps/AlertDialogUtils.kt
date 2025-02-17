package com.example.nearnear.Gps

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings

/*
 * GPSがオフになっているときにアラートを表示するメソッド
 */
fun promptUserToEnableGPS(context: Context) {
    //LocationUtilsオブジェクトの使用。GPSが有効かどうかを確認
    if (!LocationUtils.isGPSEnabled()) {
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