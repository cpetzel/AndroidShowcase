buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(deps.gradlePlugins.gradle)
        classpath(deps.gradlePlugins.junit)
        classpath(deps.gradlePlugins.kotlin)
        classpath(deps.gradlePlugins.navArgs)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }

    }
}
