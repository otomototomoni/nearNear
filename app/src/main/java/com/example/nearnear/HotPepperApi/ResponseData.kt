package com.example.nearnear.HotPepperApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
 * ホットペッパーAPIのレスポンに対して受け取るResponseの型
 *
 */
data class ResponseData(
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
    val l: String, val s: String
)
