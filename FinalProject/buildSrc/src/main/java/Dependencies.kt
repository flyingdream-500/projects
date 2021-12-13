

object Dependencies {

    object Base {
        private const val materialVersion = "1.4.0"
        private const val coreKtxVersion = "1.7.0"
        private const val appCompatVersion = "1.3.1"
        private const val constraintLayoutVersion = "2.1.1"
        private const val legacySupportVersion = "1.0.0"

        const val material = "com.google.android.material:material:$materialVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        const val appcompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val legacySupport = "androidx.legacy:legacy-support-v4:$legacySupportVersion"
    }

    object  Test {
        const val jUnit = "junit:junit:4.+"
        const val androidJUnit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"

        const val powerMockModule = "org.powermock:powermock-module-junit4:2.0.2"
        const val powerMockApi = "org.powermock:powermock-api-mockito2:2.0.2"
        const val mockk = "io.mockk:mockk:1.10.6"
        const val mockkAndroid = "io.mockk:mockk-android:1.10.6"
        const val mockitoInlineAndroid = "org.mockito:mockito-inline:3.4.4"
        const val mockitoCore = "org.mockito:mockito-core:3.10.0"
        const val archCore = "android.arch.core:core-testing:1.1.1"

        const val mockitoAndroid = "org.mockito:mockito-android:2.7.15"
        const val googleTruth = "com.google.truth:truth:1.1.3"
        const val fragmentTesting = "androidx.fragment:fragment-testing:1.4.0"
        const val testRunner = "androidx.test:runner:1.4.0"
        const val testCore = "androidx.test:core:1.4.0"
    }

    object Navigation {
        private const val navigationVersion = "2.3.5"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
    }

    object Moshi {
        private const val moshiVersion = "1.12.0"
        const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
        const val moshiTest = "androidx.test.ext:junit-ktx:1.1.3"
        const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:1.9.1"
    }

    object OkHttp {
        private const val okHttpVersion = "4.9.1"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    }

    object Rx {
        private const val rxVersion = "3.0.0"
        const val rxJava = "io.reactivex.rxjava3:rxjava:$rxVersion"
        const val rxAndroid = "io.reactivex.rxjava3:rxandroid:$rxVersion"
    }

    object Lottie {
        private const val lottieVersion = "4.2.0"
        const val lottie = "com.airbnb.android:lottie:$lottieVersion"
    }

    object Dagger {
        private const val daggerVersion = "2.36"
        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    }

    object Hilt {
        private const val hiltVersion = "2.36"
        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    }

    object HiltExtension {
        private const val hiltExtensionVersion = "1.2.0"
        const val hiltExtension = "it.czerwinski.android.hilt:hilt-extensions:$hiltExtensionVersion"
        const val hiltExtensionCompiler = "it.czerwinski.android.hilt:hilt-processor:$hiltExtensionVersion"
    }

    object Room {
        private const val roomVersion = "2.3.0"
        const val room = "androidx.room:room-runtime:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomRxJava = "androidx.room:room-rxjava3:$roomVersion"
    }

    object Glide {
        private const val glideVersion = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:$glideVersion"
        const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"
    }

    object ViewPager2 {
        private const val viewPagerVersion = "1.0.0"
        const val viewPager2 = "androidx.viewpager2:viewpager2:$viewPagerVersion"
    }



}