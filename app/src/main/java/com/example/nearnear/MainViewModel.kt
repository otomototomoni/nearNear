package com.example.nearnear

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearnear.HotPepperApi.ResponseData
import com.example.nearnear.HotPepperApi.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

/*
 * 環境変数としてAPIキーを指定しており、RECRUIT_API_KEYとして保存
 *
 * ToDO：何をしているのかを理解し、コメントとして残す
 *  APIリクエストを実行するためMainViewModel
 */
class MainViewModel : ViewModel() {
    private val _responseData = MutableStateFlow<ResponseData?>(null)
    // 外部から参照するための StateFlow
    val responseData: StateFlow<ResponseData?> = _responseData

    //APIリクエストを実行するためのメソッド
    fun getShops(latitude: Double, longitude: Double, range: Int) {
        viewModelScope.launch {
            try {
                val responseData = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getShops(
                        apiKey = BuildConfig.RECRUIT_API_KEY,
                        latitude = latitude,
                        longitude = longitude,
                        range = range,
                    )
                }
                // レスポンスデータの処理
                if (responseData != null) {
                    Log.d("responseData","データがあります。")
                    //取得したresponseDataを_responseDataに格納する
                    _responseData.value = responseData
                } else {
                    _responseData.value = null
                }
            } catch (e: Exception) {
                // エラー処理
                Log.e("MainViewModel", "Failure: ${e.message}")
                _responseData.value = null
            }
        }
    }
}