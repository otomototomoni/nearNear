package com.example.nearnear.HotPepperApi


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Retrofitクライアントのインターフェース
 *
 * ToDO: 何をしているのか理解し、コメントする。
 *  APIリクエストを定義するためのAPIServiceインターフェース
 *  こちらからAPIキーやレスポンスのフォーマットを送信することによって、APIリクエストをする
 */
interface ApiService {
    //Getリクエストのエンドポイントの指定
    @GET("hotpepper/gourmet/v1/")
    //店舗情報を取得するためのメソッド
    fun getShops(
        @Query("key") apiKey: String,//APIキー
        @Query("lat") latitude: Double,//緯度
        @Query("lng") longitude: Double,//経度
        @Query("range") range: Int,//検索範囲の指定
        @Query("count") count: Int = 100,//取得する店舗の数
        @Query("format") format: String = "json"//レスポンスのフォーマットの指定
    ):Call<ResponseData>//APIレスポンスをResponseData型で受け取ることを指定
}