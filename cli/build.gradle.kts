plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    macosX64 {
        binaries {
            executable {
                baseName = "kommand"
                entryPoint = "com.juul.kommand.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("com.github.ajalt.clikt:clikt:3.0.1")
            }
        }
    }
}
