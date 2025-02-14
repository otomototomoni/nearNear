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
 */
class MainViewModel : ViewModel() {
    fun getShops(latitude: Double, longitude: Double, range: Int) {
        val call = RetrofitClient.apiService.getShops(
            apiKey = BuildConfig.RECRUIT_API_KEY,
            latitude = latitude,
            longitude = longitude,
            range = range
        )

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    // レスポンスデータの処理
                    if (responseData != null) {
                        Log.d("MainViewModel", "Response: ${responseData.results.shop}")
                        // ここでresponseData.results.shopを使って店舗情報を処理する
                    }
                } else {
                    // エラー処理
                    Log.e("MainViewModel", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                // エラー処理
                Log.e("MainViewModel", "Failure: ${t.message}")
            }
        })
    }
}