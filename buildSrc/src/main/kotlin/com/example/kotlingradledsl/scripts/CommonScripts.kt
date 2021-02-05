package com.example.kotlingradledsl.scripts

import com.android.build.gradle.BaseExtension
import com.example.kotlingradledsl.properties.Dependency
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun BaseExtension.androidCommonConfig() {
    compileSdkVersion(Dependency.compileSdk)
    buildToolsVersion(Dependency.buildToolVersion)

    defaultConfig {
        minSdkVersion(Dependency.minSdk)
        targetSdkVersion(Dependency.targetSdk)
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "SAMPLE_VALUE", "\"Hello World~!\"")
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            buildConfigField("boolean", "DEBUG_VALUE", "true")
        }
        getByName("release") {
            isDebuggable = false
            buildConfigField("boolean", "DEBUG_VALUE", "false")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") {
        jvmTarget = "1.8"
        useIR = true
    }
}