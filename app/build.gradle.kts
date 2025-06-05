plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
//    alias(libs.plugins.google.services.plugin)
    alias(libs.plugins.navigation.safeargs.plugin)
}

android {
    namespace = "com.fuel.engineblue"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fuel.engineblue"
        minSdk = 26
        targetSdk = 35
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Support Libraries
    implementation(libs.androidx.appcompat)
    implementation(libs.recyclerView)
    implementation(libs.constraintLayout)
    implementation(libs.viewpager2)
    implementation(libs.androidx.core.ktx)
    implementation(libs.preference)
    implementation(libs.preference.ktx)
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.annotation)

    // Material
    implementation(libs.material)


    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Multi-dex Lib
    implementation(libs.multidex)

    // Other libs
    // Retrofit 2
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    // OkHttp 3
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.interceptor)


    // Coil
    implementation(libs.coil)


    // Chucker Inspector
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    // Jetpack Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.material3)


    // Hilt
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)

    // Navigation
    implementation(libs.navigationCompose)

    //Dexter
    implementation(libs.dexter)

    // Charts
    implementation(libs.charts)
}