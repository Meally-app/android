import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    id("org.jmailen.kotlinter") version "5.0.1"
    id("com.google.gms.google-services") version "4.4.2" apply false
}

subprojects {
    apply(plugin = "org.jmailen.kotlinter")
}

kotlinter {

}

