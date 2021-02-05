package com.example.kotlingradledsl.properties

object Dependency {
    const val kotlinVersion = "1.4.21"
    const val androidGradleVersion = "4.1.1"
    const val minSdk = 16
    const val compileSdk = 30
    const val targetSdk = 30
    const val buildToolVersion = "30.0.3"


    object ClassPath {
        const val androidGradle = "com.android.tools.build:gradle:$androidGradleVersion"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appComat = "androidx.appcompat:appcompat:1.2.0"

        const val material = "com.google.android.material:material:1.3.0"
        const val contstraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    }


    object Test {
        const val junit4 = "junit:junit:4.13.1"
    }
}