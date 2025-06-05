import com.android.build.api.dsl.ProductFlavor
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.0-1.0.22"
    kotlin("plugin.serialization") version "2.0.20"
    id("com.google.gms.google-services")
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    namespace = "com.meally.meally"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.meally.meally"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
    }

    flavorDimensions.add("environment")

    productFlavors {
        create("prod") {
            dimension = "environment"
            loadProperties("prod")
        }

        create("dev") {
            dimension = "environment"
            loadProperties("dev")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
    buildFeatures {
        compose = true
        buildConfig = true
    }

}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.mlkit.barcode.scanning)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.logging.interceptor)

    // ### coil ###
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // ### destinations ###
    implementation(libs.destinations.animations.core)
    ksp(libs.destinations)

    // ### androidx camera ###
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)

    // ### firebase ###
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // ### testing ###
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ### charts ###
    implementation(libs.vico.compose.m3)
}

fun ProductFlavor.loadProperties(flavor: String) {
    val file = rootProject.file("$flavor.properties")
    if (file.exists()) {
        val properties = Properties().apply { load(file.inputStream()) }
        properties.forEach { key, value ->
            val valueStr = value.toString()
            val type =
                when {
                    valueStr.equals("true", ignoreCase = true) ||
                        valueStr.equals("false", ignoreCase = true) -> "boolean"
                    valueStr.toIntOrNull() != null -> "int"
                    else -> "String"
                }
            buildConfigField(
                type, key.toString().uppercase(), if (type == "String") "\"$valueStr\"" else valueStr)
        }
    } else {
        throw GradleException("Missing properties file: ${file.path}")
    }
}
