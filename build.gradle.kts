import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20"
    id("org.jetbrains.compose") version "0.2.0-build132"
}

group = "fbla"
version = "1.0"

// Edit stuff later

repositories {
    google()
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}


dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation( "com.squareup.okhttp3:okhttp:3.8.1")
    implementation("net.jemzart:jsonkraken:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
}



tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            packageName = "FBLAQuizPortal"
            version = "0.2"
            description = "FBLA Quiz Portal for FBLA SLC Coding&Programming"
            copyright = "Eclipse License. All rights reserved."
            vendor = "FBLAQuizPortal"

            windows {
                iconFile.set(project.file("src/main/resources/icon.ico"))
            }
        }
    }
}