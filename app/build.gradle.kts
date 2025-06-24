plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.10"
    // ✅ ADD THESE HILT PLUGINS
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt") // For annotation processing
}

android {
    namespace = "com.interstellar.rahulpihujetpackdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.interstellar.rahulpihujetpackdemo"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.foundation.layout.android)

    //Material for xml based only ,old way
    implementation(libs.material) //Useful only for View-based Android UI


    //constrain layout
    implementation (libs.androidx.constraintlayout.compose)
//    implementation(libs.material3)      // we req material3 compose temporary commented
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.tv.material)

    // ✅ ADD HILT DEPENDENCIES HERE
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ✅ Hilt Compose integration
    implementation(libs.androidx.hilt.navigation.compose)

    // ✅ Optional: Hilt testing (if you need testing)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)


    // 🔄 ViewModel & Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // 🌐 Networking (Retrofit & OkHttp)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // 📝 Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json.v173)
    implementation(libs.retrofit2.kotlinx.serialization.converter)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}