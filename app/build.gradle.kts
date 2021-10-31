
plugins {
    id ("com.android.application")
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

    //Test
    testImplementation (Dependencies.Test.jUnit)
    androidTestImplementation (Dependencies.Test.androidJUnit)
    androidTestImplementation (Dependencies.Test.espresso)
}