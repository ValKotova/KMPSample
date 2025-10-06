plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":shared:core:core-domain"))
    implementation(project(":shared:core:core-data"))
    implementation(project(":shared:core:core-presentation"))
    implementation(project(":shared:feature:feature-game"))
    implementation(project(":shared:feature:feature-settings"))
    implementation(project(":shared:feature:feature-history"))
    implementation(project(":shared:di"))
    implementation(compose.desktop.currentOs)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.desktop)
    // Add your other dependencies
}

compose.desktop {
    application {
        mainClass = "com.magni.game2048.MainKt"

        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "game2048"
            packageVersion = "1.0.0"
        }
    }
}