import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.8.15"
}

group = "com.uramnoil"
version = "0.1"

repositories {
    mavenCentral()
}

val protobuf_version="3.17.3"
val grpc_version="1.39.0"
val grpc_kotlin_version="1.1.0"
val kotlin_version = "1.5.31"
val napier_version = "2.1.0"
val coroutines_version = "1.5.2"
val kodein_version = "7.6.0"

dependencies {
    implementation("io.github.aakira:napier:$napier_version")
    implementation("org.kodein.di:kodein-di:$kodein_version")
    implementation("org.kodein.di:kodein-di-framework-compose:$kodein_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("com.google.protobuf:protobuf-java-util:$protobuf_version")
    implementation("io.grpc:grpc-netty-shaded:$grpc_version")
    implementation("io.grpc:grpc-protobuf:$grpc_version")
    implementation("io.grpc:grpc-kotlin-stub:$grpc_kotlin_version")

    testImplementation(kotlin("test"))
}

java {
    sourceSets.main {
        java.srcDirs("build/generated/source/proto/main/grpc")
        java.srcDirs("build/generated/source/proto/main/java")
    }
}

kotlin {
    sourceSets.main {
        kotlin.srcDirs("build/generated/source/proto/main/grpckt")
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobuf_version"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpc_version${if (osdetector.os == "osx") ":osx-x86_64" else ""}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpc_kotlin_version:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}