import deps.android.build.buildToolsVersion
import deps.android.build.compileSdkVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(deps.android.build.compileSdkVersion)
    buildToolsVersion(deps.android.build.buildToolsVersion)

    defaultConfig {
        applicationId = "com.petzel.dev.android.androidshowcase"
        minSdkVersion(deps.android.build.minSdkVersion)
        targetSdkVersion(deps.android.build.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.findByName("release")
            proguardFiles("proguard-android.txt")
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true

            // Set false to use R8
            isUseProguard = true
        }

        getByName("debug") {
            signingConfig = signingConfigs.findByName("debug")
            extra.set("enableCrashlytics", false)
            proguardFiles("proguard-android.txt")
            (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    kapt(deps.kapt.dagger)
    kapt(deps.kapt.room)

    implementation(deps.rx.autodispose)
    implementation(deps.rx.autodisposeAndroidKotlin)
    implementation(deps.rx.autodisposeArch)
    implementation(deps.rx.autodisposeKotlin)
    implementation(deps.rx.autodisposeLifecycle)
    implementation(deps.rx.autodisposeLifecycleKotlin)

    implementation(deps.kotlin.stdlib.core)
    implementation(deps.android.androidx.arch)
    implementation(deps.android.androidx.navFragment)
    implementation(deps.android.androidx.navUi)
    implementation(deps.android.androidx.appcompat)
    implementation(deps.android.androidx.corektx)
    implementation(deps.android.androidx.constraintLayout)
    implementation(deps.android.androidx.swipeToRefresh)
    implementation(deps.android.androidx.material)
    implementation(deps.android.androidx.recyclerView)
    implementation(deps.android.androidx.paging)
    implementation(deps.reporting.timber)
    implementation(deps.networking.retrofit)
    implementation(deps.networking.retrofitGson)
    implementation(deps.networking.retrofitRx2)
    implementation(deps.networking.okhttp)
    implementation(deps.networking.okhttpLogging)
    implementation(deps.dagger.core)
    implementation(deps.android.androidx.room)
    implementation(deps.android.androidx.roomRx)
    implementation(deps.ui.snacky)
    implementation(deps.ui.toasty)
    implementation(deps.ui.glide)
    implementation(deps.ui.materialProgressBar)
    implementation(deps.rxBinding.core)

    debugImplementation(deps.chuck.debug)
    debugImplementation(deps.util.flipper.core)
    debugImplementation(deps.util.flipper.flipperSO)
    debugImplementation(deps.util.flipper.flipperNetwork)
    debugImplementation(deps.debug.leakCanaryDebug)
    debugImplementation(deps.debug.leakCanaryFlipper)

    releaseImplementation(deps.debug.leakCanaryRelease)
    releaseImplementation(deps.util.flipper.flipperRelease)
    releaseImplementation(deps.chuck.release)

    testImplementation(deps.test.junit)
}
