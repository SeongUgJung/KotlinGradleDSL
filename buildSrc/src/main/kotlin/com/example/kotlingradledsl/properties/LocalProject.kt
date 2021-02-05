package com.example.kotlingradledsl.properties

import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMembers

object LocalProject {
    const val library = ":library"
}

fun DependencyHandlerScope.appLocalProjectDependencies() {
    val localProjects = buildLocalProjects(LocalProject::class)
    for ((_, projectName) in localProjects) {
        "implementation"(project(projectName))
    }
}

fun buildLocalProjects(kClass: KClass<out Any>): HashMap<String, String> {
    val locals = hashMapOf<String, String>()

    for (member in kClass.declaredMembers) {
        locals[member.name] = member.call() as String
    }

    return locals
}

