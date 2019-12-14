
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(deps.gradlePlugins.gradle)
        classpath(deps.gradlePlugins.junit)
        classpath(deps.gradlePlugins.kotlin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
