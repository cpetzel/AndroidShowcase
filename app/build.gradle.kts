plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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
            setCrunchPngs(true)

            // Set false to use R8
            setUseProguard(true)
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
    implementation(deps.kotlin.stdlib.core)
    implementation(deps.android.androidx.appcompat)
    implementation(deps.android.androidx.corektx)
    implementation(deps.android.androidx.constraintLayout)
    implementation(deps.android.androidx.material)


    testImplementation(deps.test.junit)
}
