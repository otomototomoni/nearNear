plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.nearnear"
    compileSdk =35

    defaultConfig {
        applicationId = "com.example.nearnear"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //環境変数からAPIキーを取得するやつ APIキーを環境変数で"RECRUIT_API_KEY"として保存している
            buildConfigField("String", "RECRUIT_API_KEY", "\"${System.getenv("RECRUIT_API_KEY")}\"")
        }
        //環境変数からAPIキーを取得するやつ。debugだからテストで使う用？
        debug {
            buildConfigField("String", "RECRUIT_API_KEY", "\"${System.getenv("RECRUIT_API_KEY")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    //Gsonの追加
    implementation (libs.gson)
    //fused location providerの追加
    implementation (libs.google.play.services.location)
    implementation(libs.coil.compose)
    //api実装のために記述したもの
    //Retrofit本体
    implementation(libs.retrofit)
    //RetrofitでGsonを使用するためのコンバーター
    implementation(libs.retrofit2.converter.gson)
    //Retrofitが内部で使用するHttpクライアント
    implementation(libs.okhttp)
    //デバッグ用。リクエストとレスポンスをログに出力
    implementation(libs.logging.interceptor)
    //ViewModelを使用するためのもの
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.contentpager)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.animation.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}