package com.example.nearnear

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * 環境変数としてAPIキーを指定しており、RECRUIT_API_KEYとして保存
 *
 * ToDO：何をしているのかを理解し、コメントとして残す
 *  APIリクエストを実行するためMainViewModel
 */
class MainViewModel : ViewModel() {
    //APIリクエストを実行するためのメソッド
    fun getShops(latitude: Double, longitude: Double, range: Int) {
        val call = RetrofitClient.apiService.getShops(
            //build.gradle.ktsで設定したAPIキーを使用
            apiKey = BuildConfig.RECRUIT_API_KEY,
            //検索条件として緯度、経度、範囲を受け取る
            latitude = latitude,
            longitude = longitude,
            range = range
        )

        //call.enqueue：非同期でAPIリクエストを実行する
        call.enqueue(object : Callback<ResponseData> {
            //APIリクエストが成功した場合に呼び出される
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    // レスポンスデータの処理
                    if (responseData != null) {
                        Log.d("MainViewModel", "Response: ${responseData.results.shop}")
                        //ToDO: ここでresponseData.results.shopを使って店舗情報を処理する
                    }
                } else {
                    // エラー処理
                    Log.e("MainViewModel", "Error: ${response.code()}")
                }
            }

            //APIリクエストが失敗したときに呼び出される
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                // エラー処理
                Log.e("MainViewModel", "Failure: ${t.message}")
            }
        })
    }
}