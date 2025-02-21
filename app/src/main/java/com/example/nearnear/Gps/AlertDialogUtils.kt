package com.example.nearnear.Gps

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.ui.platform.LocalContext

/*
 * アラートダイアログをまとめているオブジェクト
 */
object AlertDialogUtils {
    //GPS機能の許可を求める
    fun promptUserToEnableGPS(context: Context) {
        val LocationUtils = LocationUtils()
        LocationUtils.init(context)
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

    //ネットワークのパーミッションの許可を求める
    fun promptUserToEnableNetwork(context: Context){
        val LocationUtils = LocationUtils()
        LocationUtils.init(context)
        //LocationUtilsオブジェクトの使用。GPSが有効かどうかを確認
    if (!LocationUtils.isNetworkEnabled()) {
            AlertDialog.Builder(context)
                .setMessage("ネットワークがオフになっています。位置情報を正確に取得するためには、ネットワークをオンにしてください。")
                .setPositiveButton("設定") { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    context.startActivity(intent)
                }
                .setNegativeButton("キャンセル", null)
                .create()
                .show()
        }
    }
}