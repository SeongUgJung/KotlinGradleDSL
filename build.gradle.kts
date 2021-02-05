buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        // these should be full path in root build.gradle.kts
        classpath(com.example.kotlingradledsl.properties.Dependency.ClassPath.androidGradle)
        classpath(com.example.kotlingradledsl.properties.Dependency.ClassPath.kotlin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}