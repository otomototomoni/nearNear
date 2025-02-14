package com.example.nearnear


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Retrofitクライアントのインターフェース
 *
 * ToDO: 何をしているのか理解し、コメントする。ResponseDataのエラーを治す
 */
interface ApiService {
    @GET("hotpepper/gourmet/v1/")
    fun getShops(
        @Query("key") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("range") range: Int,
        @Query("format") format: String = "json"
    ): Call<ResponseData>
}