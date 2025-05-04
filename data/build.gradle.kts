plugins {
  id("java-library")
  alias(libs.plugins.jetbrains.kotlin.jvm)
  kotlin("plugin.serialization") version "2.0.20"
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

kotlin { compilerOptions { jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11 } }

dependencies {
  implementation(project(":domain"))

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.jvm)
  implementation(project.dependencies.platform(libs.koin.bom))
  implementation(libs.koin.core)

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.retrofit)
  implementation(libs.retrofit.gson)
  implementation(libs.logging.interceptor)

}
