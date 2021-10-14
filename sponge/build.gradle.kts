plugins {
    kotlin("jvm") version "1.5.31"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://repo.spongepowered.org/maven")
}

val coroutines_version = "1.5.2"

dependencies {
    implementation(project(":common"))

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    compileOnly("org.spongepowered:spongeapi:7.2.0")
}