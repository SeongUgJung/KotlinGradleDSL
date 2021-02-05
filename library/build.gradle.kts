import com.example.kotlingradledsl.properties.Dependency

plugins {
    id("custom-lib")
}

android {
    // buildconfig 생성하지 않기
    libraryVariants.all {
        generateBuildConfigProvider.configure {
            enabled = false
        }
    }
}

dependencies {
    // 디펜던시 추가하기
    implementation(Dependency.AndroidX.material)
}