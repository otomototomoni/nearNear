package com.example.nearnear.HotPepperApi

/*
 * Retrofitクライアントのレスポンスデータのクラス
 *
 * ToDo：何をしているのか調べる
 *  ホットペッパーAPIから孵されるJSONデータをマッピングするためのデータクラス
 *  ApiServiceでリクエストを送り、どのような情報が欲しいのかをここで定義する
 */

//APIレスポンス全体のデータクラス
data class ResponseData (
    val results: Results
)

data class Results(
    val shop: List<Shop>
)

data class Shop(
    val name: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val access: String,
    val open: String,
    val photo: Photo
)

data class Photo(
    val mobile: Mobile
)

data class Mobile(
    val l: String,
    val s: String
)