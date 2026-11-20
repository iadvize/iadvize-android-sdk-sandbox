plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.androidx.navigation.safeargs.kotlin) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.gradle.versions.plugin)
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}

val kotlinVersion = libs.versions.kotlin.asProvider().get()

subprojects {
    // kotlin-stdlib-jdk7/-jdk8 are empty since Kotlin 1.8 but transitive deps still pin them to 1.9.x.
    // Align them with Kotlin on app classpaths only: lint tool classpaths need their own Kotlin version.
    configurations.matching { it.name.endsWith("CompileClasspath") || it.name.endsWith("RuntimeClasspath") }.configureEach {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-stdlib-jdk")) {
                useVersion(kotlinVersion)
            }
        }
    }
}
