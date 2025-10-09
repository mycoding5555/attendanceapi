plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "kotlinapi"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.xerial:sqlite-jdbc:3.42.0.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	// Password hashing using jBCrypt
	implementation("org.mindrot:jbcrypt:0.4")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

// Ensure Kotlin bytecode target matches the dependencies (set to 17)
tasks.withType<KotlinCompile> {
	kotlinOptions {
		// Match the Java toolchain (Java 21) to avoid JVM target mismatch
		jvmTarget = "21"
		// preserve the explicit nullability behavior we had before
		freeCompilerArgs += listOf("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
