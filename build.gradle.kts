plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
	// (removed application plugin to avoid interfering with bootRun)
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

// Provide a small JavaExec task to run the HashGenerator helper without changing bootRun behavior
tasks.register<JavaExec>("genHash") {
	group = "tools"
	description = "Generate a bcrypt hash for a password. Use -Ppwd=yourPassword"
	// runtimeClasspath is available from the main source set
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set("tools.HashGeneratorKt")
	if (project.hasProperty("pwd")) {
		args = listOf(project.property("pwd").toString())
	}
}

// Ensure bootRun uses the Spring Boot application main class explicitly to avoid ambiguity
tasks.named("bootRun") {
	// Specify the Spring Boot main class explicitly
	(this as org.gradle.api.tasks.JavaExec).mainClass.set("kotlinapi.AttendanceApi.AttendanceApiApplicationKt")
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
