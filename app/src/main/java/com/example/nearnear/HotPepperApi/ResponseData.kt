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
    val photo: Photo
)

data class Photo(
    val pc: Pc
)
data class Pc(
    val l: String,
    val m: String,
    val s: String
)