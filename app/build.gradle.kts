
plugins {
    id ("com.android.application")
    id ("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.compileSdk



    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //Base
    implementation (Dependencies.Base.appcompat)
    implementation (Dependencies.Base.coreKtx)
    implementation (Dependencies.Base.material)
    implementation (Dependencies.Base.constraintLayout)
    implementation (Dependencies.Base.legacySupport)

    //Navigation
    implementation (Dependencies.Navigation.navigationFragment)
    implementation (Dependencies.Navigation.navigationUi)

    //Moshi
    implementation (Dependencies.Moshi.moshi)
    implementation (Dependencies.Moshi.moshiKotlin)
    implementation (Dependencies.Moshi.moshiTest)
    kapt (Dependencies.Moshi.moshiKotlinCodegen)

    //OkHttp
    implementation(Dependencies.OkHttp.okHttp)

    //Rx
    implementation(Dependencies.Rx.rxJava)
    implementation(Dependencies.Rx.rxAndroid)

    //Lottie
    implementation(Dependencies.Lottie.lottie)

    //Dagger2
    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.daggerCompiler)

    //Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.hiltCompiler)
    //HiltExtension
    implementation(Dependencies.HiltExtension.hiltExtension)
    kapt(Dependencies.HiltExtension.hiltExtensionCompiler)

    //Glide
    implementation(Dependencies.Glide.glide)
    kapt(Dependencies.Glide.glideCompiler)


    //Room
    implementation(Dependencies.Room.room)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.Room.roomRxJava)
    kapt(Dependencies.Room.roomCompiler)

    //Test
    testImplementation (Dependencies.Test.jUnit)
    androidTestImplementation (Dependencies.Test.androidJUnit)
    androidTestImplementation (Dependencies.Test.espresso)


}