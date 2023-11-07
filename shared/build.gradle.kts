plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
    kotlin("plugin.serialization") version "1.9.0"
}

val version = block@{ dep: String -> project.properties["${dep}.version"].toString() }

kotlin {
    androidTarget()

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation("io.ktor:ktor-client-core:${version("ktor")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${version("kotlinx-serialization-json")}")
                implementation("app.cash.sqldelight:primitive-adapters:${version("sqldelight")}")
                implementation("app.cash.sqldelight:coroutines-extensions:${version("sqldelight")}")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${version("kotlinx-datetime")}")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.8.0")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                implementation("io.ktor:ktor-client-android:${version("ktor")}")
                implementation("app.cash.sqldelight:android-driver:${version("sqldelight")}")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:${version("ktor")}")
                implementation("app.cash.sqldelight:native-driver:${version("sqldelight")}")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation("io.ktor:ktor-client-apache5:${version("ktor")}")
                implementation("app.cash.sqldelight:sqlite-driver:${version("sqldelight")}")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.lennartmoeller.ma.composemultiplatform"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

sqldelight {
    databases {
        create("FinanceDatabase") {
            packageName.set("com.lennartmoeller.ma.composemultiplatform.database")
        }
    }
}
