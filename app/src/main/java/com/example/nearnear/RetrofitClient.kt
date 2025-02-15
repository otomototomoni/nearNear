package com.example.nearnear

//RetrofitCLientオブジェクトを動かすためのimport
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Retrofitクライアントのオブジェクト
 *
 * ToDO: 何をしているのか理解し、コメントする。
 *  Retrofitを使用してAPIリクエストを行うためのクライアント
 */
object RetrofitClient {
    //ホットペッパーAPIのベースURLを定義
    private const val BASE_URL = "https://webservice.recruit.co.jp/"

    //Retrofitを使用してApiServiceインターフェースの実装を作成
    val apiService: ApiService by lazy {
        //リクエストとレスポンスをログに出力するためのインターセプターを作成
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        //HttpLoggingInterceptorを追加したOkHttpClientを作成
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        Retrofit.Builder()
            //ベースURLの設定
            .baseUrl(BASE_URL)
            //Gsonを使用してJSONをオブジェクトに変換するためのコンバーターファクトリを追加
            .addConverterFactory(GsonConverterFactory.create())
            //OkHttpClientを設定
            .client(client)
            .build()
            //ApiServiceインターフェースの実装を作成
            .create(ApiService::class.java)
    }
}