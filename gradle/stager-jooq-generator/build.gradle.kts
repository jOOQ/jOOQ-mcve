plugins {
  java
  kotlin("jvm") version "1.9.22"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jooq:jooq-codegen:3.19.12")
}

kotlin {
  // Use Gradle's JVM Toolchain feature: https://docs.gradle.org/current/userguide/toolchains.html
  jvmToolchain(21)
}
