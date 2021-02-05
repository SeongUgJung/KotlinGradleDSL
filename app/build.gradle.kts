import com.example.kotlingradledsl.properties.Dependency
import com.example.kotlingradledsl.properties.appLocalProjectDependencies
import com.example.kotlingradledsl.scripts.androidCommonConfig

plugins {
    id("com.android.application")

    kotlin("android")
    kotlin("kapt")
}

android {
    androidCommonConfig()

    defaultConfig {
        versionCode(1)
        versionName("1.0")
    }

    buildTypes {

        getByName("release") {
            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(Dependency.Kotlin.stdlib)
    implementation(Dependency.AndroidX.coreKtx)
    implementation(Dependency.AndroidX.appComat)
    implementation(Dependency.AndroidX.material)
    implementation(Dependency.AndroidX.contstraintlayout)
    testImplementation(Dependency.Test.junit4)

    appLocalProjectDependencies()
}