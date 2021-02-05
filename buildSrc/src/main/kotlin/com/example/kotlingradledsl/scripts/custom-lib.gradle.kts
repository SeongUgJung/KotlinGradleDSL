import com.example.kotlingradledsl.properties.Dependency
import com.example.kotlingradledsl.scripts.androidCommonConfig

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    androidCommonConfig()
}


dependencies {

    Dependency.Kotlin.run {
        implementation(stdlib)
    }

    Dependency.Test.run {
        testImplementation(junit4)
    }
}