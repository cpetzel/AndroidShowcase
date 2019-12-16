@file:Suppress("unused")

// found from https://proandroiddev.com/how-to-manage-dependencies-in-multi-module-project-84620afbb415

object deps {
    object versions {
        const val kotlin = "1.3.50"
        const val autodispose = "1.1.0"
        const val rxBinding = "3.0.0-alpha2"
        const val dagger = "2.22"
        const val retrofit = "2.5.0"
        const val okhttp = "3.14.0"
        const val stetho = "1.5.1"
        const val chuck = "1.1.0"
        const val androidxVersion = "1.0.0"
        const val androidLifecycle = "2.0.0"
        const val kotlinVersion = "1.3.50"
        const val okhttpVersion = "3.14.0"
        const val retrofitVersion = "2.5.0"
        const val rxBindingsVersion2 = "2.2.0"
        const val rxBindingsVersion3 = "3.0.0"
        const val room = "2.2.2"
        const val navigation = "1.0.0"
        const val navAgrs = "2.1.0"
    }

    object android {
        object androidx {
            const val annotations = "androidx.annotation:annotation:${versions.androidxVersion}"
            const val appcompat = "androidx.appcompat:appcompat:1.1.0"
            const val arch = "androidx.lifecycle:lifecycle-extensions:2.0.0"
            const val cardView = "androidx.cardview:cardview:${versions.androidxVersion}"
            const val collection = "androidx.collection:collection:${versions.androidxVersion}"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
            const val corektx = "androidx.core:core-ktx:1.1.0"
            const val emoji = "androidx.emoji:emoji-appcompat:${versions.androidxVersion}"
            const val exif = "androidx.exifinterface:exifinterface:${versions.androidxVersion}"
            const val lifecycle =
                "androidx.lifecycle:lifecycle-runtime:${versions.androidLifecycle}"
            const val material = "com.google.android.material:material:1.1.0-beta01"
            const val media = "androidx.media:media:${versions.androidxVersion}"
            const val multidex = "androidx.multidex:multidex:2.0.0"
            const val paging = "androidx.paging:paging-rxjava2:2.1.0"
            const val pagingRuntime = "androidx.paging:paging-runtime:2.1.0"
            const val recyclerView =
                "androidx.recyclerview:recyclerview:${versions.androidxVersion}"
            const val v13 = "androidx.legacy:legacy-support-v13:${versions.androidxVersion}"
            const val v4 = "androidx.legacy:legacy-support-v4:${versions.androidxVersion}"
            const val workRuntime = "android.arch.work:work-runtime:1.0.0-rc02"
            const val workRxJava2 = "android.arch.work:work-rxjava2:1.0.0-rc02"

            const val navFragment =
                "android.arch.navigation:navigation-fragment-ktx:${versions.navigation}"
            const val navUi = "android.arch.navigation:navigation-ui-ktx:${versions.navigation}"


            const val room = "androidx.room:room-runtime:${versions.room}"
            const val roomRx = "androidx.room:room-rxjava2:${versions.room}"

            object test {
                const val androidTestCore = "androidx.test:core:1.1.0"
                const val androidTestRules = "androidx.test:rules:1.1.0"
                const val androidTestExt = "androidx.test.ext:junit:1.1.0"
            }
        }

        object build {
            const val buildToolsVersion = "29.0.0"
            const val compileSdkVersion = 29
            const val minSdkVersion = 21
            const val targetSdkVersion = 29
        }
    }

    object kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${versions.kotlin}"

        object stdlib {
            const val core = "org.jetbrains.kotlin:kotlin-stdlib:${versions.kotlin}"
            const val jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.kotlin}"
        }
    }

    object javax {
        const val inject = "javax.inject:javax.inject:1"
    }

    object gradlePlugins {
        const val android = "com.android.tools.build:gradle:3.4.0"
        const val dependencyCheck = "org.owasp:dependency-check-gradle:5.0.0-M2"
        const val gradle = "com.android.tools.build:gradle:3.4.0"
        const val junit = "de.mannodermaus.gradle.plugins:android-junit5:1.4.0.0"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"
        const val navArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${versions.navAgrs}"
    }

    object dagger {
        const val core = "com.google.dagger:dagger:${versions.dagger}"
    }

    object io {
        object retrofit {
            const val core = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
            const val rx2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${versions.retrofit}"
        }

        object okhttp {
            const val core = "com.squareup.okhttp3:okhttp:${versions.okhttp}"

            object debug {
                const val logging = "com.squareup.okhttp3:logging-interceptor:${versions.okhttp}"
            }
        }
    }

    object kapt {
        const val autoFactory = "com.google.auto.factory:auto-factory:1.0-beta6"
        const val dagger = "com.google.dagger:dagger-compiler:${versions.dagger}"
        const val room = "androidx.room:room-compiler:${versions.room}"
    }

    object rxBinding {
        const val core = "com.jakewharton.rxbinding3:rxbinding-core:${versions.rxBinding}"
    }

    object stetho {
        object debug {
            const val core = "com.facebook.stetho:stetho:${versions.stetho}"
            const val okhttp = "com.facebook.stetho:stetho-okhttp3:${versions.stetho}"
            const val timber = "com.facebook.stetho:stetho-timber:${versions.stetho}"
        }
    }

    object chuck {
        const val debug = "com.readystatesoftware.chuck:library:${versions.chuck}"
        const val release = "com.readystatesoftware.chuck:library-no-op:${versions.chuck}"
    }

    object rx {
        const val autodispose = "com.uber.autodispose:autodispose:${versions.autodispose}"
        const val autodisposeAndroid =
            "com.uber.autodispose:autodispose-android:${versions.autodispose}"
        const val autodisposeAndroidKotlin =
            "com.uber.autodispose:autodispose-android-ktx:${versions.autodispose}"
        const val autodisposeArch =
            "com.uber.autodispose:autodispose-android-archcomponents-ktx:${versions.autodispose}"
        const val autodisposeKotlin = "com.uber.autodispose:autodispose-ktx:${versions.autodispose}"
        const val autodisposeLifecycleKotlin =
            "com.uber.autodispose:autodispose-lifecycle-ktx:${versions.autodispose}"
        const val autodisposeLifecycle =
            "com.uber.autodispose:autodispose-lifecycle:${versions.autodispose}"
        const val location = "pl.charmas.android:android-reactive-location2:2.1@aar"
        const val rx = "io.reactivex.rxjava2:rxjava:2.2.4"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.0"
        const val rxBinding = "com.jakewharton.rxbinding3:rxbinding:${versions.rxBindingsVersion3}"
        const val rxBindingKotlin =
            "com.jakewharton.rxbinding2:rxbinding-kotlin:${versions.rxBindingsVersion2}"
        const val rxBindingAppCompat =
            "com.jakewharton.rxbinding3:rxbinding-recyclerview:${versions.rxBindingsVersion3}"
        const val rxBindingDesign =
            "com.jakewharton.rxbinding2:rxbinding-design-kotlin:${versions.rxBindingsVersion2}"
        const val rxBindingMaterial =
            "com.jakewharton.rxbinding3:rxbinding-material:${versions.rxBindingsVersion3}"
        const val rxBindingRecyclerView =
            "com.jakewharton.rxbinding3:rxbinding-recyclerview:${versions.rxBindingsVersion3}"
        const val rxBindingSupportV4 =
            "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${versions.rxBindingsVersion2}"
        const val rxBindingSupportV7 =
            "com.jakewharton.rxbinding2:rxbinding-appcompat-v7-kotlin:${versions.rxBindingsVersion2}"
        const val rxExtras = "com.github.davidmoten:rxjava2-extras:0.1.20"
        const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"
        const val rxRelay = "com.jakewharton.rxrelay2:rxrelay:2.1.0"
    }

    object networking {
        const val okhttp = "com.squareup.okhttp3:okhttp:${versions.okhttpVersion}"
        const val okhttpLogging =
            "com.squareup.okhttp3:logging-interceptor:${versions.okhttpVersion}"
        const val reactiveNetwork = "com.github.pwittchen:reactivenetwork-rx2:3.0.2"
        const val retrofit = "com.squareup.retrofit2:retrofit:${versions.retrofitVersion}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${versions.retrofitVersion}"
        const val retrofitRx2 = "com.squareup.retrofit2:adapter-rxjava2:${versions.retrofitVersion}"
    }

    object ui {
        const val glide = "com.github.bumptech.glide:glide:4.6.1"
        const val material = "com.google.android.material:material:1.0.0"
        const val materialDialog = "com.afollestad.material-dialogs:core:0.9.6.0"
        const val materialDialogCommon = "com.afollestad.material-dialogs:commons:0.9.6.0"
        const val materialEditText = "com.rengwuxian.materialedittext:library:2.1.4"
        const val materialProgressBar = "me.zhanghai.android.materialprogressbar:library:1.4.2"
        const val photoView = "com.github.chrisbanes:PhotoView:2.1.3"
        const val progressBar = "com.github.castorflex.smoothprogressbar:library-circular:1.1.0"
        const val scaleImage = "com.davemorrissey.labs:subsampling-scale-image-view:3.10.0"
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
        const val snacky = "com.github.matecode:Snacky:1.0.3"
        const val spinnerDate = "com.github.drawers:SpinnerDatePicker:1.0.6"
        const val tapTargetView = "com.getkeepsafe.taptargetview:taptargetview:1.9.1"
        const val toasty = "com.github.GrenderG:Toasty:1.2.8"
        const val tooltips = "com.tomergoldst.android:tooltips:1.0.10"
        const val viewPagerIndicator = "com.inkapplications.viewpageindicator:library:2.4.3"
    }

    object reporting {
        const val timber = "com.jakewharton.timber:timber:4.5.1"
    }

    object util {
        const val gson = "com.google.code.gson:gson:2.8.0"
        const val joda = "net.danlew:android.joda:2.9.9.4"
        const val stetho = "com.facebook.stetho:stetho:1.5.0"
        const val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:1.5.0"

        object flipper {
            const val core = "com.facebook.flipper:flipper:0.30.1"
            const val flipperRelease = "com.facebook.flipper:flipper-noop:0.30.1"
            const val flipperSO = "com.facebook.soloader:soloader:0.5.1"
            const val flipperNetwork = "com.facebook.flipper:flipper-network-plugin:0.30.1"
        }
    }


    object debug {
        const val chuck = "com.readystatesoftware.chuck:library:1.1.0"
        const val leakCanaryDebug = "com.squareup.leakcanary:leakcanary-android:1.5.3"
        const val leakCanaryRelease = "com.squareup.leakcanary:leakcanary-android-no-op:1.5.3"
    }

    object test {
        const val androidTestRunner = "com.android.support.test:runner:1.0.2"
        const val androidTestArchCore = "android.arch.core:core-testing:1.1.0"
        const val assertj = "org.assertj:assertj-core:2.4.1"
        const val commonsLogging = "commons-logging:commons-logging:1.2"
        const val joda = "joda-time:joda-time:2.9.9"
        const val junit = "junit:junit:4.12"
        const val junitParams = "pl.pragmatists:JUnitParams:1.1.1"
        const val jupiter = "org.junit.jupiter:junit-jupiter-api:5.3.1"
        const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:5.3.1"
        const val vintageEngine = "org.junit.vintage:junit-vintage-engine:5.3.1"
        const val jupiterParams = "org.junit.jupiter:junit-jupiter-params:5.3.1"
        const val mockK = "io.mockk:mockk:1.9.1"
        const val mockito = "org.mockito:mockito-core:2.15.0"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0"
        const val okHttp3 = "com.squareup.okhttp3:mockwebserver:${versions.okhttpVersion}"
        const val retrofit2 = "com.squareup.retrofit2:retrofit-mock:${versions.retrofitVersion}"
        const val robolectric = "org.robolectric:robolectric:4.3"
    }
}
