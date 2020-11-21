buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    kotlin("multiplatform") version "1.4.20" apply false
    kotlin("plugin.serialization") version "1.4.20" apply false
}

subprojects {
    repositories {
        jcenter()
    }
}
