import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Base {
        private const val materialVersion = "1.4.0"
        private const val coreKtxVersion = "1.7.0"
        private const val appCompatVersion = "1.3.1"
        private const val constraintLayoutVersion = "2.1.1"

        const val material = "com.google.android.material:material:$materialVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        const val appcompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    }

    object Test {
        const val jUnit = "junit:junit:4.+"
        const val androidJUnit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
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
        val list = arrayListOf<String>(rxJava, rxAndroid)
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

}