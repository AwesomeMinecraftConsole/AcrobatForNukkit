plugins {
    kotlin("jvm") version "1.5.31"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://repo.spongepowered.org/maven")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("org.spongepowered:spongeapi:7.2.0")
}