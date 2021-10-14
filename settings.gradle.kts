rootProject.name = "acrobat"

pluginManagement {
    plugins {
        val kotlin_version: String = "1.5.31"
        kotlin("multiplatform") version kotlin_version
        kotlin("jvm") version kotlin_version
    }
}

include(":common", ":sponge")