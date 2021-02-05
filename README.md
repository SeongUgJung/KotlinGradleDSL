**Warning**

현재 작성된 예제는 Android Gradle Plugin 버전에 따라서 정상 동작하지 않을 수 있습니다.

# Android Gradle Kotlin DSL

Gradle Kotlin DSL 에서 빌드스크립트용 전역 변수를 사용하기 위해서는 buildSrc 내에 설정이 필요합니다.

## buildSrc

이 프로젝트에는 {rootProjectPath}/buildSrc 에는 3가지 종류의 빌드스크립트가 있습니다.

- 빌드스크립트용 전역 변수
- 공유 가능한 빌드스크립트 설정 함수
- 주문형 빌드스크립트


## 빌드스크립트용 전역 변수
1. buildSrc/build.gradle.kts 설정
```kotlin
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}
```

2. 전역변수 만들기
```kotlin
// buildSrc/src/main/{kotlin|java}/{package-path}/Dependency.kt
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
```

3. 전역 변수 사용
```kotlin
// build.gradle.kts
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        // 전역 변수에 대해 전체 경로를 다 해야합니다.
        classpath(com.example.kotlingradledsl.properties.Dependency.ClassPath.androidGradle)
        classpath(com.example.kotlingradledsl.properties.Dependency.ClassPath.kotlin)
    }
}

// app/build.gradle.kts
import com.example.kotlingradledsl.properties.Dependency

dependencies {
  // 전역 변수에 대해 전체 경로를 다 해야합니다.
  implementation(Dependency.Kotlin.stdlib)
}
```
여기서 선언한 전역변수는 buildSrc/build.gradle.kts 를 제외한 모든 영역에서 사용가능합니다.

## 빌드스크립트용 함수
1. buildSrc/build.gradle.kts 설정
```kotlin
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}
```

2. 함수 선언
```kotlin
// buildSrc/src/main/{kotlin|java}/{package-path}/LalProject.kt
fun buildLocalProjects(kClass: KClass<out Any>): HashMap<String, String> {
    val locals = hashMapOf<String, String>()

    for (member in kClass.declaredMembers) {
        locals[member.name] = member.call() as String
    }

    return locals
}
```

3. 함수 사용
```kotlin
// app/build.gradle.kts
dependencies {
  buildLocalProjects().values.forEach { localProjectName ->
    implementation(project(localProjectName)
  }
}
```

## 확장 함수 사용하기
buildSrc 에서 확장 함수를 사용하기 위해서는 buildSrc/build.gradle.kts 에 Gradle 과 관련된 의존성을 추가하여야 합니다.

1. buildSrc/build.gradle.kts 설정하기
```kotlin
plugins {
    `kotlin-dsl`
}
val androidGradleVersion = "4.1.1"
val kotlinVersion = "1.4.21"

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = kotlinVersion
}

repositories {
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:$androidGradleVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}
```
2. 함수 선언하기
아래의 BaseExtension 은 안드로이드의 application/library 모듈 모두 접근 가능한 객체입니다.
```kotlin
// buildSrc/src/main/{kotlin|java}/{package-path}/CommonScripts.kt
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
```

3. 확장 함수 사용하기
```
import com.example.kotlingradledsl.scripts.androidCommonConfig
plugins {
  id("com.android.library") // or id("com.android.application")
}
android {
  androidCommonConfig()
}
```

## 공용 스크립트 적용하기
1. buildSrc/build.gradle.kts 설정하기 : 확장함수 사용하기 설정 그대로 이용.

2. custom-lib.gradle.kts 생성하기
```kotlin
// buildSrc/src/main/{kotlin|java}/{package-path}/custom-lib.gradle.kts
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
```
이 모듈은 `id("com.android.library")` 타겟으로 만들었기 때문에 라이브러리 모듈용으로 사용하실 수 있습니다.

3. custom-lib.gradle.kts 적용하기

위의 스크립트는 `custom-lib` 라는 자체 gradle-id 를 가지게 됩니다. 따라서 `id("custom-lib")` 라는 플러그인으로 적용 가능합니다.kotlin
```kotlin
// {library-module}/build.gradle.kts
plugins {
    id("custom-lib")
}
```

Gradle Kotlin DSL 쓰는 사람들에게... Good Luck
-끗-